package br.usp.ime.cogroo.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.cogroo.model.DictionaryEntry;
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
	@Path("/insertEntry")
	public void insertEntry(String word, DictionaryEntry dictionaryEntry) {
		String entry = word; 
		entry += parseCategory(dictionaryEntry);
		result.redirectTo(getClass()).newEntry(entry);
	}
	
	public String parseCategory(DictionaryEntry dictionaryEntry) {
		String res = new String();
		switch(dictionaryEntry.getCategory()) {
			case VERB:
				res = "/v";
				break;
			case COMMON_NOUN:
				res = "/nm";
		default:
			break;
		}
		return res;
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
				json_result = searchWord(text);
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
	
	public static LinkedList<Vocable> searchWord(String text) throws IOException{
		LinkedList<Vocable> vocables = new LinkedList<Vocable>();
        HttpClient client = new DefaultHttpClient();
        String url = "http://logprob.ime.usp.br:4040/jspell/analyse.json?id=default&lexeme=";
        HttpGet request = new HttpGet(url + text);
        HttpResponse response;
        Scanner scanner;
        JSONObject wordDescriptor = null;
		try {
			response = client.execute(request);
			scanner = new Scanner(response.getEntity().getContent());
			wordDescriptor = new JSONObject(scanner.nextLine());
			JSONArray analisis = wordDescriptor.getJSONArray("analise");

			for (int i = 0; i < analisis.length(); i++){
				JSONObject json = analisis.getJSONObject(i);
				Vocable v = new Vocable(json.getString("CAT"), text, json.getString("rad"));

				Iterator<String> jsonIterator = json.keys();
				while(jsonIterator.hasNext()){
					String key = jsonIterator.next();
					if (!(key.equals("CAT") || key.equals("rad"))){
						v.addProperty(key, json.getString(key));
					}
				}
				vocables.add(v);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return vocables;
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
