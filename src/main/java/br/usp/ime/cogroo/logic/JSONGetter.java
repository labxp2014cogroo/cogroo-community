package br.usp.ime.cogroo.logic;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONGetter {
	
	public static JSONObject getJSONFromWebService(String serviceURL, String suffix) throws IOException
	{
		String encodedURL = serviceURL + URLEncoder.encode(suffix, "ISO-8859-1"); 
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(encodedURL);
        HttpResponse response;
        Scanner scanner;
        JSONObject jsonResult = null;
		try {
			response = client.execute(request);
			scanner = new Scanner(response.getEntity().getContent());
			jsonResult = new JSONObject(scanner.nextLine());
			scanner.close();
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return jsonResult; 
	}
}
