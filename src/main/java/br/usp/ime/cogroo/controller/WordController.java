package br.usp.ime.cogroo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.usp.ime.cogroo.dao.DictionaryPatchDAO;
import br.usp.ime.cogroo.exceptions.ExceptionMessages;
import br.usp.ime.cogroo.logic.DerivationsQuery;
import br.usp.ime.cogroo.logic.SearchWordJspell;
import br.usp.ime.cogroo.model.DictionaryPatch;
import br.usp.ime.cogroo.model.Flags;
import br.usp.ime.cogroo.model.LoggedUser;
import br.usp.ime.cogroo.model.Vocable;
import br.usp.ime.cogroo.security.annotations.LoggedIn;


@Resource
public class WordController {
	
	private DictionaryPatchDAO dictionarypatchdao;
	private Result result;
	private Validator validator;
	private LoggedUser loggedUser;
	private HttpServletRequest request;
	
	public WordController(Result result, Validator validator, LoggedUser loggedUser, HttpServletRequest request, DictionaryPatchDAO dictionarypatchdao) {
		this.result = result;
		this.validator = validator;
		this.loggedUser = loggedUser;
		this.request = request;
		this.dictionarypatchdao = dictionarypatchdao;
	}

	@Path("/dictionaryEntrySearch")
	public void dictionaryEntrySearch() {
		
	}
	
	@Path("/newEntry/loggedUser")
	public void verifyLoggedUser(String word) {
		if (word != null) {
			request.getSession().setAttribute("word", word);
		}

		if(loggedUser.isLogged()) {

			if (word == null) {
				word = (String) request.getSession().getAttribute("word");
			}
			result.redirectTo(getClass()).newEntry(word);
		}
		else {
			result.redirectTo(LoginController.class).login();
		}
	}
	
	@LoggedIn
	@Path("/newEntry")
	public void newEntry(String word) {
		result.include("word", word);
	}
	
	@Post
	@Path("/chooseCategory")
	public void chooseCategory(String word, String category) {
		if (category == null) {
			validator.add(new ValidationMessage(ExceptionMessages.NO_CATEGORY_SELECTED, ExceptionMessages.ERROR));
		}
		
		result.include("word", word);
		result.include("entry", word + "/CAT=" + category + ",");
		result.include("category", category);
		validator.onErrorUsePageOf(getClass()).newEntry(word);
		result.redirectTo(getClass()).grammarProperties(word);
	}
	
	
	@Path("/grammarProperties")
	public void grammarProperties(String word) {
		result.include("word", word);
	}
	
	@Post
	@Path("/chooseProperties")
	public void chooseProperties(String word, String entry, String gender, String number, String transitivity, String type) {
		
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
		
		HashMap<String, String> derivations = DerivationsQuery.queryDerivations(entry);
		

		result.include("word", word);
		result.include("entry", entry);
		result.include("derivations", derivations);
		
		HashMap<String, String> mapFlags = new HashMap<String, String>();
		
		Flags flags;
		try {
			flags = Flags.getInstance();
			Set<String> keys = derivations.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()){
				String key = it.next();
				mapFlags.put(key, flags.getTextFromFlag(key));
			}
			result.include("flagsTexts", mapFlags);	
		} catch (IOException e) {
			result.include("flagsTexts", null);
		}
		
		result.redirectTo(getClass()).derivations(word);
	}
	
	@Path("/derivations")
	public void derivations(String word) {
		result.include("word", word);
	}
	
	@Post
	@Path("/chooseFlags")
	public void chooseFlags(String entry, String[] flag) {
		
		for (String f : flag) {
			entry += f;
		}
	
		DictionaryPatch dictionarypatch = new DictionaryPatch();
		dictionarypatch.setNewEntry(entry);
		dictionarypatch.setUser(loggedUser.getUser());
		dictionarypatchdao.add(dictionarypatch);

		System.out.println(entry);
		
		result.redirectTo(getClass()).dictionaryEntrySearch();
	}
	

	@Post
	@Path("/searchEntry")
	public void searchEntry(String text) throws JSONException {
		LinkedList<Vocable> vocablesList;
		String status = "status";
		String mensagem_erro = "mensagem_erro";
		
		try { 
			if (text == null || text.length() < 1) {
				validator.add(new ValidationMessage(ExceptionMessages.EMPTY_FIELD, ExceptionMessages.EMPTY_FIELD));
				validator.onErrorUsePageOf(getClass()).dictionaryEntrySearch();
			}
			else {
				vocablesList = SearchWordJspell.searchWord(text);
				result.include("typed_word", text);
				
				if (vocablesList.isEmpty()){
					result.include(mensagem_erro, "Essa palavra não consta no dicionário");
					result.include(status, 404);
				} else {
					result.include("vocables", vocablesAsStrings(vocablesList));
					result.include(status, 0);
				}
			}
		}
		catch (IOException e) {
			result.include(mensagem_erro, "Serviço fora do ar");
			result.include(status, 501);
		}
		result.redirectTo(getClass()).dictionaryEntrySearch();
	}
	
	
	public String[][] vocablesAsStrings (LinkedList<Vocable> vocables) {
		
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
	
}
