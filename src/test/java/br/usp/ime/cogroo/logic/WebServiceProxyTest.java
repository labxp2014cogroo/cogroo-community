package br.usp.ime.cogroo.logic;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;

@Category(LabXP2014.class)
public class WebServiceProxyTest {

	private final String REPO = "TestRepo"; 
	
	@Test
	public void testTryRequest() throws FileNotFoundException, IOException, JSONException {
		JSONObject result = WebServiceProxy.getInstance().tryRequest("abismal/#an/dFGALO");
		JSONArray array = (JSONArray) result.get("analise"); 
		assertEquals(array.getJSONObject(0).get("CAT"), "adj");
		assertEquals(array.getJSONObject(0).get("rad"), "abismal");
		
	}
	
	@Test
	public void testAnalisisRequest() throws FileNotFoundException, IOException, JSONException {
		JSONObject result = WebServiceProxy.getInstance().analysisRequest("casa");
		assertEquals( ((JSONObject) result.get("derivadas")).getJSONObject("casas").get("rad"), "casa");
		assertEquals( ((JSONObject) result.get("derivadas")).getJSONObject("casas").get("G"), "f");
	}
	
	@Test
	public void testAnalisisRequestWordsWithPREAO90() throws FileNotFoundException, IOException, JSONException {
		JSONObject result = WebServiceProxy.getInstance().analysisRequest("ativar");
		assertEquals( ((JSONArray) result.getJSONArray("analise")).getJSONObject(0).get("PREAO90"), "activar");
	}
	
	@Test 
	public void testCreateEntry () throws FileNotFoundException, JSONException, IOException {
		boolean result = WebServiceProxy.getInstance().load(REPO); 
		assertEquals(true, result);
		
		result = WebServiceProxy.getInstance().create(REPO, "orkut/#nm//"); 
		assertEquals(true, result);
		
		result = WebServiceProxy.getInstance().create(REPO, "Lalaler/CAT=v,T=inf,TR=t/EPa");
		assertEquals(true, result);
	}
	
	@Test
	public void testCommit () throws FileNotFoundException, JSONException, IOException {
		boolean result = WebServiceProxy.getInstance().commit(REPO, "commit test", "message");
		assertEquals(true, result); 		
	}
}
