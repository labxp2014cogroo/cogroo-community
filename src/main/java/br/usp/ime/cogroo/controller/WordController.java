package br.usp.ime.cogroo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
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
	
	@Post
	@Path("/trataPalavra")
	public void trataPalavra(String word) throws ClientProtocolException, IOException, JSONException {
		String mensagem = "";
		if (word == null) {
			mensagem = "ERRO!!!";
		}
		else if (word.length() == 0) {
			mensagem = "Nenhuma palavra foi digitada!";
		}
		else { 
			//JSONObject teste =  buscaPalavra(word);   
			//mensagem = teste.toString(); 
			mensagem = "Inserindo a palavra: " + word; 
		}
		result.include("mensagem", mensagem).redirectTo(this).newEntry();
	}
	
	public static JSONArray consultWord(String text){
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
