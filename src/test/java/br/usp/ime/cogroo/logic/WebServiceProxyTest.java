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

}