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
	
	public static HashMap<String, String> queryDerivations(String text) {
		HashMap<String, String> derivationsHash = null;
		try {
			JSONObject jsonResult = JSONGetter.getJSONFromWebService(baseProcessURL, text + FLAGS).getJSONObject("derivadas");
			derivationsHash = getRelevantDerivations(jsonResult);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		return derivationsHash;
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
