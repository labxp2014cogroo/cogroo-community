package br.usp.ime.cogroo.logic;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.usp.ime.cogroo.model.Vocable;

public class SearchWordJspell {
	
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
	
	//Acho que num eh mais de vocable 
	public static LinkedList<String> searchUnknownWords (String text) throws IOException {
		
		LinkedList<String> unknownWords = new LinkedList<String>(); 
		StringTokenizer tokens = new StringTokenizer(text, " ,.!?();:");
		String token;
		
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (!existsInJspell(token)){
				unknownWords.add(token);
			}
		}
		
		return unknownWords; 
	}
	
	public static boolean existsInJspell (String word) throws IOException {
		return (!SearchWordJspell.searchWord(word).isEmpty()); 
	}

}
