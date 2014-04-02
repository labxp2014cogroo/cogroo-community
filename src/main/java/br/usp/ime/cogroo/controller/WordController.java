package br.usp.ime.cogroo.controller;

import java.io.IOException;
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
		JSONArray json_result;
		try { 
			json_result = searchWord(text);
			String valueJSON = json_result.getJSONObject(0).getString("analise"); 	
			if (valueJSON.equals("[]")) {
				result.include("mensagem_erro", "Palavra " + text +" não existe");
				result.include("typed_word", text);
				result.include("cod_erro", 404);
			} else {
				result.include("json_result", json_result.toString());
			}
		}
		catch (IOException e) {
			result.include("mensagem_erro", "Serviço fora do ar");
			result.include("cod_erro", 501);
		}
		result.redirectTo(getClass()).dictionaryEntrySearch();
	}
	
	public static JSONArray searchWord(String text) throws IOException {
		HttpClient client = new DefaultHttpClient();
		String url = "http://logprob.ime.usp.br:4040/query.json?palavra=";
		HttpGet request = new HttpGet(url + text);
		HttpResponse response;
		Scanner scanner;
		JSONArray wordDescriptor = null;
		try {
			response = client.execute(request);
			scanner = new Scanner(response.getEntity().getContent());
			wordDescriptor = new JSONArray(scanner.nextLine());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return wordDescriptor;
	}
}
