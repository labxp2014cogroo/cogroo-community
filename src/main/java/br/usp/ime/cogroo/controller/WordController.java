package br.usp.ime.cogroo.controller;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
	public void newEntry() {
	}

	@Path("/dictionaryEntrySearch")
	public void dictionaryEntrySearch() {
	}

	@Post
	@Path("/searchEntry")
	public void searchEntry(String text) {

	}
	
	@Post
	@Path("/trataPalavra")
	public void trataPalavra(String word) {
		String mensagem = "";
		if (word == null) {
			mensagem = "ERRO!!!";
		}
		else if (word.length() == 0) {
			mensagem = "Nenhuma palavra foi digitada!";
		}
		else {
			//Tem que chamar o metodo de busca de palavras no jspell com word
			mensagem = "Vc digitou: " + word;
		}
		result.include("mensagem", mensagem).redirectTo(this).newEntry();
	}
	
	private JSONObject nomeDisso(String text) throws ClientProtocolException, IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://200.18.49.108:4040/query.json?palavra=" + text);
        HttpResponse response = client.execute(request);
        Scanner scanner = new Scanner(response.getEntity().getContent());
        try {
			JSONObject wordDescriptor = new JSONObject(scanner.nextLine());
			return wordDescriptor;
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return null;
	}

}
