package br.usp.ime.cogroo.logic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.batik.util.io.UTF8Decoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


public class DerivationsQuery {
	static final String FLAGS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static final String baseProcessURL = "http://logprob.ime.usp.br:4040/jspell/try.json?entry=";
	
	private DerivationsQuery() 
	{
		return;
	}

	
	/**
	 * 
	 * @param text é um paramêtro do formato radical/classificação/
	 * @return um HashMap que associa uma flag à palavra gerada
	 */
	
	@SuppressWarnings("deprecation")
	public static HashMap<String, String> queryDerivations(String text) {
		HashMap<String, String> derivationsHash = null;
		try {
			String encodedURL = URLEncoder.encode(text + FLAGS);
			JSONObject allDerivations = getJSONFromWebService(baseProcessURL + encodedURL);
			derivationsHash = getRelevantDerivations(allDerivations);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return derivationsHash;
	}
	
	public static JSONObject getJSONFromWebService(String encodedURL) throws IOException
	{
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(encodedURL);
        HttpResponse response;
        Scanner scanner;
        JSONObject wordDescriptor = null, allDerivations = null;
		try {
			response = client.execute(request);
			scanner = new Scanner(response.getEntity().getContent());
			wordDescriptor = new JSONObject(scanner.nextLine());
			allDerivations = wordDescriptor.getJSONObject("derivadas");
			scanner.close();
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return allDerivations; 
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getRelevantDerivations(JSONObject allDerivations) 
	{
			HashMap<String, String> relevantDerivations = new HashMap<String, String>(); 			
			Iterator<String> jsonIterator = allDerivations.keys(); 
			while(jsonIterator.hasNext()){
				String derivation = jsonIterator.next();
				try {
					if (allDerivations.getJSONObject(derivation).has("flags")) {
						String flag = allDerivations.getJSONObject(derivation).getString("flags");  
						if (flag.length() == 1)
							relevantDerivations.put(flag, derivation);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		
		return relevantDerivations;
	}
}
