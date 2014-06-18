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

	public DictionaryPatchController(Result result, Validator validator,
			LoggedUser loggedUser, HttpServletRequest request,
			DictionaryPatchDAO dictionaryPatchDAO) {
		this.result = result;
		this.validator = validator;
		this.loggedUser = loggedUser;
		this.request = request;
		this.dictionaryPatchDAO = dictionaryPatchDAO;
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

	@Path("/newEntry/loggedUser")
	public void verifyLoggedUser(String word) {
		String user_word;
		if (word != null) {
			request.getSession().setAttribute("word", word);
		}

		if (loggedUser.isLogged()) {
			if (word == null) {
				user_word = (String) request.getSession().getAttribute("word");
			} else {
				user_word = word;
			}
			result.redirectTo(getClass()).newEntry(user_word);
		} else {
			result.redirectTo(LoginController.class).login();
		}
	}

	@Post
	public void patchApproval(String[] flags, long idPatch) {
		DictionaryPatch dictionaryPatch = dictionaryPatchDAO.retrieve(idPatch);

		dictionaryPatch.setState(State.RESOLVED);

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
	public void patchDisapproval(String[] flags, long idPatch) {
		DictionaryPatch dictionaryPatch = dictionaryPatchDAO.retrieve(idPatch);

		dictionaryPatch.setState(State.REJECTED);

		result.redirectTo(getClass()).dictionaryEntries();
	}

	@Get
	@Path("/dictionaryPatch/entriesDetails/{patch.id}")
	public void entriesDetails(DictionaryPatch dictionaryPatch) {
		if (dictionaryPatch == null) {
			result.redirectTo(getClass()).dictionaryEntries();
			return;
		}

		result.include("dictionaryPatch", dictionaryPatch);
	}

	@Post
	public void searchEntry(String text) throws JSONException {
		List<Vocable> vocablesList;
		String status = "status";
		String mensagem_erro = "mensagem_erro";

		try {
			if (text == null || text.length() < 1) {
				validator.add(new ValidationMessage(
						ExceptionMessages.EMPTY_FIELD,
						ExceptionMessages.EMPTY_FIELD));
				validator.onErrorUsePageOf(DictionaryPatchController.class)
						.dictionaryEntrySearch();
			} else {
				vocablesList = SearchWordJspell.searchWord(text);
				result.include("typed_word", text);

				if (vocablesList.isEmpty()) {
					result.include(mensagem_erro,
							"Essa palavra não consta no dicionário");
					result.include(status, 404);
				} else {
					result.include("vocables", vocablesAsStrings(vocablesList));
					result.include(status, 0);
				}
			}
		} catch (IOException e) {
			result.include(mensagem_erro, "Serviço fora do ar");
			result.include(status, 501);
		}
		result.redirectTo(DictionaryPatchController.class).newEntry(text);
	}

	public String[][] vocablesAsStrings(List<Vocable> vocables) {

		String[][] descriptions = new String[vocables.size()][3];

		int i = 0;

		for (Vocable vocable : vocables) {
			descriptions[i][0] = vocable.getRadical();
			descriptions[i][1] = vocable.getCategory();
			descriptions[i][2] = vocable.getPropertiesAsString();
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
		result.include("word", word);
		String entry = word + "/CAT=" + category;
		validator.onErrorUsePageOf(getClass()).newEntry(word);

		if (category.equals("in")) {
			entry += "//";
			result.redirectTo(getClass()).chooseFlags(entry, new String[0]);
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
			String number, String transitivity, String type) {

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

		Map<String, String> derivations = DerivationsQuery
				.queryDerivations(entry);

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

		for (String f : flag) {
			entry += f;
		}

		DictionaryPatch dictionarypatch = new DictionaryPatch();
		dictionarypatch.setNewEntry(entry);
		dictionarypatch.setUser(loggedUser.getUser());
		dictionaryPatchDAO.add(dictionarypatch);

		result.include("okMessage", "Palavra cadastrada com sucesso!");

		result.redirectTo(DictionaryPatchController.class).dictionaryEntries();
	}
}