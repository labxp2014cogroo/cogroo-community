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
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import br.usp.ime.cogroo.controller.RuleController;

public class WebServiceProxy {
	private static WebServiceProxy singleton = null;
	private String baseURL;
	private Properties webServiceProperties;
	private static String propertiesFileName = "src/main/resources/webservice.properties";
	private static final String REPO_NAME = "repoCogrooCommunity";
	private static final Logger LOG = Logger.getLogger(RuleController.class);

	public String getBaseURL() {
		return baseURL;
	}

	private WebServiceProxy() throws FileNotFoundException, IOException {
		this.webServiceProperties = new Properties();
		this.webServiceProperties.load(new FileInputStream(new File(
				WebServiceProxy.propertiesFileName)));
		this.baseURL = this.webServiceProperties.getProperty("baseURL").trim();
	}

	public static WebServiceProxy getInstance() throws FileNotFoundException,
			IOException {
		if (WebServiceProxy.singleton == null) {
			WebServiceProxy.singleton = new WebServiceProxy();
		}
		return WebServiceProxy.singleton;
	}

	public JSONObject tryRequest(String text) throws IOException, JSONException {
		return this.getJSONFromWebService(this.webServiceProperties
				.getProperty("try") + URLEncoder.encode(text, "ISO-8859-1"));
	}

	public JSONObject analysisRequest(String text) throws IOException, JSONException {
		return this.getJSONFromWebService(this.webServiceProperties
				.getProperty("analysis") + text);
	}

	public JSONObject retrieveRequest(String lemma) throws IOException, JSONException {
		return this.getJSONFromWebService(this.webServiceProperties
				.getProperty("retrieve") + lemma);
	}

	private JSONObject getJSONFromWebService(String suffix) throws IOException, JSONException {
		String requestURL = this.baseURL + suffix;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(requestURL);
		HttpResponse response;
		Scanner scanner;
		JSONObject jsonResult = null;
		response = client.execute(request);
		scanner = new Scanner(response.getEntity().getContent());
		jsonResult = new JSONObject(scanner.nextLine());
		scanner.close();
		return jsonResult;
	}

	public boolean insertEntry(String entry, String message)
			throws IOException, JSONException {
		if (this.load(REPO_NAME)) {
			if (this.create(REPO_NAME, entry)) {
				if (this.commit(REPO_NAME, entry, message)) {
					if (this.push(REPO_NAME))
						return this.load("default");
				}
			}
		}
		return false;
	}

	protected boolean push(String repo) throws IOException, JSONException {
		JSONObject result = getJSONFromWebService(this.webServiceProperties
				.getProperty("push") + "id=" + repo);
		return result.get("status").equals("OK");
	}

	protected boolean create(String repo, String entry) throws JSONException,
			IOException {
		JSONObject result = getJSONFromWebService(this.webServiceProperties
				.getProperty("create")
				+ "id="
				+ repo
				+ "&category=geral&entry=" + entry);
		return result.get("status").equals("OK");
	}

	protected boolean commit(String repo, String entry, String message)
			throws JSONException, IOException {
		message = URLEncoder.encode(message, "UTF-8");
		JSONObject result = getJSONFromWebService(this.webServiceProperties
				.getProperty("commit") + "id=" + repo + "&message=" + message);
		return result.get("status").equals("OK");
	}

	protected boolean load(String repo) throws IOException, JSONException {
		JSONObject result = getJSONFromWebService(this.webServiceProperties
				.getProperty("load") + "id=" + repo);
		return result.get("status").equals("OK");
	}
}
