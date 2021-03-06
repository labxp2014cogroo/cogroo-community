package br.usp.ime.cogroo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.usp.ime.cogroo.dao.DictionaryPatchDAO;
import br.usp.ime.cogroo.exceptions.ExceptionMessages;
import br.usp.ime.cogroo.logic.DerivationsQuery;
import br.usp.ime.cogroo.logic.SearchWordJspell;
import br.usp.ime.cogroo.logic.WebServiceProxy;
import br.usp.ime.cogroo.model.DictionaryPatch;
import br.usp.ime.cogroo.model.Flags;
import br.usp.ime.cogroo.model.LoggedUser;
import br.usp.ime.cogroo.model.Vocable;
import br.usp.ime.cogroo.model.errorreport.State;
import br.usp.ime.cogroo.notifiers.Notificator;
import br.usp.ime.cogroo.security.annotations.LoggedIn;

@Resource
public class DictionaryPatchController {
	private static final Logger LOG = Logger
			.getLogger(DictionaryPatchController.class);

	private Result result;
	private DictionaryPatchDAO dictionaryPatchDAO;
	private Validator validator;
	private LoggedUser loggedUser;
	private HttpServletRequest request;
	private Notificator notificator;

	public DictionaryPatchController(Result result, Validator validator,
			LoggedUser loggedUser, HttpServletRequest request,
			DictionaryPatchDAO dictionaryPatchDAO, Notificator notificator) {
		this.result = result;
		this.validator = validator;
		this.loggedUser = loggedUser;
		this.request = request;
		this.dictionaryPatchDAO = dictionaryPatchDAO;
		this.notificator = notificator;
	}

	@Get
	public void getPatchDetails(Long idPatch) throws JSONException {
		DictionaryPatch dictionaryPatch = dictionaryPatchDAO.retrieve(idPatch);
		JSONObject response = new JSONObject();
		try {
			response.put("ok", 0);
			if (dictionaryPatch == null) {
				response.put("status", 1);
				response.put("msg", "Erro: o id passado não existe no banco");
				result.use(Results.http()).body(response.toString());
			} else {
				response.put("status", 0);
				response.put("msg", "OK");
				Map<String, Set<String>> derivationsHash = DerivationsQuery
						.getDerivationsFromFlags(dictionaryPatch.getNewEntry());
				JSONObject jsonDerivations = new JSONObject(derivationsHash);

				response.put("derivations", jsonDerivations);
				response.put("tipo", "inserção");
			}
		} catch (JSONException e) {
			response.put("status", 2);
			response.put("msg",
					"Erro: houve algum problema ao tentar pegar as derivações (Webservice ?)");
		}
		result.use(Results.http()).body(response.toString());
	}

	@Get
	@Path("/dictionaryEntries")
	public void dictionaryEntries() {
		List<DictionaryPatch> dictionaryPatchList = new ArrayList<DictionaryPatch>();

		dictionaryPatchList = dictionaryPatchDAO.retrieveAll();

		result.include("dictionaryPatchList", dictionaryPatchList);
	}

	@Get
	@Path("/dictionaryEntrySearch")
	public void dictionaryEntrySearch() {
	}

	@LoggedIn
	public void newEntry(String word) {
		result.include("word", word);
	}

	@Path("newEntry/loggedUser")
	public void verifyLoggedUser(String word) {
		String userWord;
		if (word != null) {
			request.getSession().setAttribute("word", word);
		}

		if (loggedUser.isLogged()) {
			if (word == null) {
				userWord = (String) request.getSession().getAttribute("word");
			} else {
				userWord = word;
			}
			result.redirectTo(getClass()).newEntry(userWord);
		} else {
			result.redirectTo(LoginController.class).login();
		}
	}

	@Post
	@Path("/patchApproval")
	public void patchApproval(String[] flags, long idPatch) {
		DictionaryPatch dictionaryPatch = dictionaryPatchDAO.retrieve(idPatch);

		dictionaryPatch.setState(State.RESOLVED);

		sendMail(dictionaryPatch, true);

		String newEntry = dictionaryPatch.getNewEntry().substring(0,
				dictionaryPatch.getNewEntry().lastIndexOf("/") + 1);
		if (flags != null) {
			for (String flag : flags) {
				newEntry += flag;
			}
		}

		try {
			String message = newEntry + " aprovada por "
					+ loggedUser.getUser().getName();

			WebServiceProxy webService = WebServiceProxy.getInstance();
			webService.insertEntry(newEntry, message);

		} catch (FileNotFoundException e) {
			LOG.error("Could not find WebService properties file", e);
		} catch (IOException e) {
			LOG.error("Communication problem", e);
		} catch (JSONException e) {
			LOG.error("Error while parsing JSON", e);
		}

		result.redirectTo(getClass()).dictionaryEntries();
	}

	@Post
	@Path("/patchDisapproval")
	public void patchDisapproval(String[] flags, long idPatch) {
		DictionaryPatch dictionaryPatch = dictionaryPatchDAO.retrieve(idPatch);

		dictionaryPatch.setState(State.REJECTED);

		sendMail(dictionaryPatch, false);

		result.redirectTo(getClass()).dictionaryEntries();
	}

	@Get
	@Path("/dictionaryPatchDetails/{patch.id}")
	public void entriesDetails(DictionaryPatch dictionaryPatch) {
		if (dictionaryPatch == null) {
			result.redirectTo(getClass()).dictionaryEntries();
			return;
		}

		result.include("dictionaryPatch", dictionaryPatch);
	}

	@Post
	public void searchEntry(String word) {
		List<Vocable> vocablesList;
		String status = "status";
		String mensagemErro = "mensagem_erro";

		try {
			if (word == null || word.length() < 1 || !word.matches("^\\w+$")) {
				validator.add(new ValidationMessage(
						ExceptionMessages.INVALID_ENTRY,
						ExceptionMessages.INVALID_ENTRY));
				validator.onErrorUsePageOf(getClass()).dictionaryEntrySearch();
			} else {
				vocablesList = SearchWordJspell.searchExistingWord(word);
				result.include("typed_word", word);

				if (vocablesList.isEmpty()) {
					result.include(mensagemErro,
							"Essa palavra não consta no dicionário");
					result.include(status, 404);
				} else {
					result.include("vocables", vocablesAsStrings(vocablesList));
					// result.include("vocables", vocablesList);
					result.include(status, 0);
				}
			}
		} catch (IOException e) {
			result.include(mensagemErro, "Serviço fora do ar");
			result.include(status, 501);
		} catch (JSONException e) {
			validator.add(new ValidationMessage("Serviço fora do ar",
					ExceptionMessages.ERROR));
			validator.onErrorUsePageOf(getClass()).dictionaryEntrySearch();
		}
		result.redirectTo(DictionaryPatchController.class)
				.dictionaryEntrySearch();
	}

	public String[][] vocablesAsStrings(List<Vocable> vocables) {

		String[][] descriptions = new String[vocables.size()][4];
		int i = 0;
		for (Vocable vocable : vocables) {
			descriptions[i][0] = vocable.getCategory();
			descriptions[i][1] = vocable.getRadical();
			descriptions[i][2] = vocable.getPropertiesAsString();
			descriptions[i][3] = vocable.getEntry();
			i++;
		}

		return descriptions;
	}

	@Post
	public void chooseCategory(String word, String category) {
		if (category == null) {
			validator.add(new ValidationMessage(
					ExceptionMessages.NO_CATEGORY_SELECTED,
					ExceptionMessages.ERROR));
		}

		// Reconfere se a palavra existe
		try {
			if (SearchWordJspell.existsInJspell(word)) {
				validator.add(new ValidationMessage(
						ExceptionMessages.EXISTING_WORD,
						ExceptionMessages.ERROR));
			}
		} catch (IOException e) {
			LOG.error("Servidor fora do ar");
		} catch (JSONException e) {
			LOG.error("Servidor fora do ar");
		}

		// Trata maiúsculas e minúsculas:
		if (category.equals("np")) {
			word = word.substring(0, 1).toUpperCase()
					+ word.substring(1).toLowerCase().trim();
		} else {
			word = word.toLowerCase().trim();
		}

		result.include("word", word);
		result.include("category", category);

		String entry = word + "/CAT=" + category;
		validator.onErrorUsePageOf(getClass()).newEntry(word);

		// Trata interjeições:
		if (category.equals("in")) {
			entry += "//";
			dictionaryPatchDAO.addInsertionPatch(entry, loggedUser.getUser());
			result.redirectTo(getClass()).dictionaryEntries();
		} else {
			entry += ",";
			result.include("entry", entry);
			result.include("category", category);
			result.redirectTo(getClass()).grammarProperties(word);
		}
	}

	public void grammarProperties(String word) {
		result.include("word", word);
	}

	@Post
	public void chooseProperties(String word, String entry, String gender,
			String number, String transitivity, String type, String category) {

		if (gender != null) {
			entry = entry + gender;
		}

		if (number != null) {
			entry = entry + number;
		}

		if (transitivity != null) {
			entry = entry + "T=inf," + transitivity;
		}

		if (type != null) {
			entry = entry + type;
		}

		entry = entry + "/";

		Map<String, String> derivations = DerivationsQuery.queryDerivations(
				entry, category);

		result.include("word", word);
		result.include("entry", entry);
		result.include("derivations", derivations);

		Map<String, String> mapFlags = new HashMap<String, String>();

		Flags flags;
		try {
			flags = Flags.getInstance();
			Set<String> keys = derivations.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = it.next();
				mapFlags.put(key, flags.getTextFromFlag(key));
			}
			result.include("flagsTexts", mapFlags);
		} catch (IOException e) {
			result.include("flagsTexts", null);
		}

		result.redirectTo(getClass()).derivations(word);
	}

	public void derivations(String word) {
		result.include("word", word);
	}

	@Post
	public void chooseFlags(String entry, String[] flag) {

		if (flag != null) {
			for (String f : flag) {
				entry += f;
			}
		}

		dictionaryPatchDAO.addInsertionPatch(entry, loggedUser.getUser());

		result.include("okMessage", "Palavra cadastrada com sucesso!");
		result.redirectTo(getClass()).dictionaryEntries();
	}

	@Get
	public void renameLemma() {
		/* Redirecionando apenas enquanto a edição não fica pronta. */
		result.redirectTo(getClass()).dictionaryEntrySearch();
	}

	@Post
	public void searchLemma(String word) {
		List<Vocable> vocablesList;
		String status = "status";
		String mensagemErro = "mensagem_erro";

		try {
			if (word == null || word.length() < 1) {
				validator.add(new ValidationMessage(
						ExceptionMessages.EMPTY_FIELD,
						ExceptionMessages.EMPTY_FIELD));
				validator.onErrorUsePageOf(getClass()).renameLemma();
			} else {
				vocablesList = SearchWordJspell.searchLemma(word);
				result.include("typed_word", word);
				if (vocablesList.isEmpty()) {
					result.include(mensagemErro,
							"Essa palavra não consta no dicionário");
					result.include(status, 404);
				} else {
					result.include("vocables", vocablesAsStrings(vocablesList));
					result.include(status, 0);
				}
			}
		} catch (IOException e) {
			result.include(mensagemErro, "Serviço fora do ar");
			result.include(status, 501);
		} catch (JSONException e) {
			validator.add(new ValidationMessage("Serviço fora do ar",
					ExceptionMessages.ERROR));
			validator.onErrorUsePageOf(getClass()).renameLemma();
		}
		result.redirectTo(getClass()).renameLemma();
	}

	@Get
	@Path("/dictionaryPatch/adjustLemma")
	public void adjustLemma(String word, String entry, String lemma) {
		result.include("word", word);
		result.include("entry", entry);
		result.include("lemma", lemma);
	}

	@Post
	@Path("/dictionaryPatch/correctedLemma")
	public void correctedLemma(String word, String entry, String lemma) {

		String newEntry = lemma
				+ entry.substring(entry.indexOf("/"), entry.length());

		validator.onErrorUsePageOf(getClass()).renameLemma();

		dictionaryPatchDAO.addEditionPatch(entry, newEntry,
				loggedUser.getUser());
		result.include("okMessage", "Palavra cadastrada com sucesso!");
		result.redirectTo(getClass()).dictionaryEntries();
	}

	public void sendMail(DictionaryPatch dic, boolean approved) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Preparing to send mail");
		}

		StringBuilder body = new StringBuilder();
		body.append("Olá, " + dic.getUser().getName() + "!<br><br>");

		String subject;
		String status;
		String message;
		if (approved) {
			subject = "Aprovação";
			status = "aprovada";
			message = "e já se encontra no dicionário utilizado pelo CoGrOO<br><br>";
		} else {
			subject = "Desaprovação";
			status = "rejeitada";
			message = "acompanhe os motivos no site.<br><br>";
		}

		String word = dic.getNewEntry().substring(0,
				dic.getNewEntry().indexOf("/"));

		body.append("De acordo com sua solicitação no portal CoGrOO Comunidade, informamos que a sua inserção da palavra "
				+ word + " foi " + status + ".<br>");
		body.append(message);
		body.append("Muito obrigado por nos ajudar a manter o CoGrOO sempre atualizado.<br>");
		body.append("Continue colaborando.");

		System.out.println(body);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Will send mail now");
		}
		notificator.sendEmail(body.toString(), subject, dic.getUser());
	}

}