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

	@Path("/dictionaryEntrySearch")
	public void dictionaryEntrySearch() {
	}

	@Post
	@Path("/searchEntry")
	public void searchEntry(String text) throws JSONException {
		LinkedList<Vocable> json_result;
		try { 
			json_result = searchWord(text);

			if (json_result.isEmpty()){
				result.include("mensagem_erro", "Palavra " + text +" não existe");
				result.include("typed_word", text);
				result.include("cod_erro", 404);
			} else {
				result.include("vocables", vocablesAsStrings(json_result));
			}
		}
		catch (IOException e) {
			result.include("mensagem_erro", "Serviço fora do ar");
			result.include("cod_erro", 501);
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
        JSONArray wordDescriptor = null;
		try {
			response = client.execute(request);
			scanner = new Scanner(response.getEntity().getContent());
			wordDescriptor = new JSONArray(scanner.nextLine());
			JSONArray analisis = wordDescriptor.getJSONObject(0).getJSONArray("analise");
			// para cada JSONObject
			for (int i = 0; i < analisis.length(); i++){
				JSONObject json = analisis.getJSONObject(i);
				Vocable v = new Vocable(json.getString("CAT"), text, json.getString("rad"));
				// TODO: foreach properties in json -->  v.addProperties
				Iterator<String> jsonIterator = json.keys();
				int k = 0;
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
		
		String[][] descriptions = new String[vocables.size()][4];
		
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
