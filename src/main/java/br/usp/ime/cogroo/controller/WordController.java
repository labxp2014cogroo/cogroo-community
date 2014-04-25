package br.usp.ime.cogroo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONException;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.usp.ime.cogroo.exceptions.ExceptionMessages;
import br.usp.ime.cogroo.logic.DerivationsQuery;
import br.usp.ime.cogroo.logic.SearchWordJspell;
import br.usp.ime.cogroo.model.Vocable;


@Resource
public class WordController {
	
	private final Result result;
	private final Validator validator;

	
	public WordController(Result result, Validator validator) {
		this.result = result;
		this.validator = validator;
	}

	@Path("/dictionaryEntrySearch")
	public void dictionaryEntrySearch() {
	}
	
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
		result.redirectTo(getClass()).grammarProperties();
	}
	
	
	@Path("/grammarProperties")
	public void grammarProperties() {
		
	}
	
	@Post
	@Path("/chooseProperties")
	public void chooseProperties(String entry, String gender, String number, String transitivity, String type) {
		
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
		
		result.include("entry", entry);
		
		result.include("derivations", derivations);
		result.redirectTo(getClass()).derivations();
	}
	
	@Path("/derivations")
	public void derivations() {
		
	}
	
	@Post
	@Path("/chooseFlags")
	public void chooseFlags(String entry, String[] flag) {
		
		for (String f : flag) {
			entry += f;
		}
		
//		TODO: Aqui tá a entrada!!!!
//		Falta inserir utilizando o WebService
		
		System.out.println(entry);
		
		result.redirectTo(getClass()).dictionaryEntrySearch();
	}
	

	@Post
	@Path("/searchEntry")
	public void searchEntry(String text) throws JSONException {
		LinkedList<Vocable> json_result;
		try { 
			if (text == null || text.length() < 1) {
				result.include("status", 400);
				result.include("mensagem_erro", "Palavra vazia");
			}
			else {
				json_result = SearchWordJspell.searchWord(text);
				result.include("typed_word", text);
				
				if (json_result.isEmpty()){
					result.include("mensagem_erro", "Palavra " + text +" não existe");
					result.include("status", 404);
				} else {
					result.include("vocables", vocablesAsStrings(json_result));
					result.include("status", 0);
				}
			}
		}
		catch (IOException e) {
			result.include("mensagem_erro", "Serviço fora do ar");
			result.include("status", 501);
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
