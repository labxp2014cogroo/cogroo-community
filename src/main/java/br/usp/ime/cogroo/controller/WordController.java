package br.usp.ime.cogroo.controller;

import java.io.IOException;
import java.util.LinkedList;

import org.json.JSONException;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.cogroo.logic.SearchWordJspell;
import br.usp.ime.cogroo.model.Vocable;

@Resource
public class WordController {
	
	private final Result result;
	
	public WordController(Result result) {
		this.result = result;
	}

	
	@Path("/newEntry")
	public void newEntry(String word) {
		result.include("word", word);
	}
	
	@Post
	@Path("/chooseCategory")
	public void chooseCategory(String word, String category) {
		
		result.include("word", word);
		result.include("entry", word + "/CAT=" + category + ",");
		result.include("category", category);
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
		
		result.include("entry", entry + "/");
				
		result.redirectTo(getClass()).derivations();
	}
	
	@Path("/derivations")
	public void derivations() {
		
	}
	
	@Path("/dictionaryEntrySearch")
	public void dictionaryEntrySearch() {
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
