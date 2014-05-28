package br.usp.ime.cogroo.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class WebServiceProxy {
	private static WebServiceProxy singleton = null;
	private String baseURL;
	private Properties webServiceProperties; 
	private static String propertiesFileName = "src/main/resources/webservice.properties"; 
	
	public String getBaseURL() {
		return baseURL;
	}

	private WebServiceProxy() throws FileNotFoundException, IOException {
		this.webServiceProperties = new Properties();
		this.webServiceProperties.load(new FileInputStream(new File(WebServiceProxy.propertiesFileName)));
		this.baseURL = this.webServiceProperties.getProperty("baseURL").trim(); 
	}
	
	public static WebServiceProxy getInstance() throws FileNotFoundException, IOException{
		if (WebServiceProxy.singleton == null){
			WebServiceProxy.singleton = new WebServiceProxy();
		}
		return WebServiceProxy.singleton;
	}
	
	public JSONObject tryRequest (String text) throws IOException {
		return this.getJSONFromWebService(this.webServiceProperties.getProperty("try") + URLEncoder.encode(text, "ISO-8859-1")); 
	}
	
	public JSONObject analysisRequest (String text) throws IOException {
		return this.getJSONFromWebService(this.webServiceProperties.getProperty("analysis") + text); 
	}
	
	private JSONObject getJSONFromWebService(String suffix) throws IOException
	{
		String requestURL = this.baseURL + suffix; 
		System.out.println(requestURL);
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(requestURL);
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
