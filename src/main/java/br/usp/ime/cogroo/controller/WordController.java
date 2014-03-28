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
	public void newEntry() {
	}

	@Path("/dictionaryEntrySearch")
	public void dictionaryEntrySearch() {
	}

	@Post
	@Path("/searchEntry")
	public void searchEntry(String text) {
		result.include("json_result", nomeDisso(text));
		result.redirectTo(getClass()).dictionaryEntrySearch();
	}
	
	public static JSONArray nomeDisso(String text){
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return wordDescriptor;
	}
}
