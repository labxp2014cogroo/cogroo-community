package br.usp.ime.cogroo.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class DerivationsQuery {
	static final String FLAGS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static final String baseProcessURL = "http://interno.cogroo.org:4040/jspell/try.json?entry=";

	private DerivationsQuery() {
		return;
	}

	/**
	 * 
	 * @param text
	 *            é um paramêtro do formato radical/classificação/
	 * @return um HashMap que associa uma flag à palavra gerada
	 */

	public static HashMap<String, String> queryDerivations(String text) {
		HashMap<String, String> derivationsHash = null;
		try {
			JSONObject jsonResult = JSONGetter.getJSONFromWebService(
					baseProcessURL, text + FLAGS).getJSONObject("derivadas");
			derivationsHash = getRelevantDerivations(jsonResult);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return derivationsHash;
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, String> getRelevantDerivations(
			JSONObject allDerivations) {
		HashMap<String, String> relevantDerivations = new HashMap<String, String>();
		Iterator<String> jsonIterator = allDerivations.keys();
		while (jsonIterator.hasNext()) {
			String derivation = jsonIterator.next();
			try {
				if (allDerivations.getJSONObject(derivation).has("flags")) {
					String flag = allDerivations.getJSONObject(derivation)
							.getString("flags");
					if (flag.length() == 1)
						relevantDerivations.put(flag, derivation);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return relevantDerivations;
	}

	public static HashMap<String, Set<String>> getDerivationsFromFlags(
			String entry) {

		HashMap<String, Set<String>> derivationsHash = null;

		try {
			JSONObject jsonResult = JSONGetter
									.getJSONFromWebService(baseProcessURL, entry)
									.getJSONObject("derivadas");
			derivationsHash = fillHashMapDerivations(jsonResult);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return derivationsHash;
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, Set<String>>
			fillHashMapDerivations(	JSONObject allDerivations) {
		
		HashMap<String, Set<String>> derivations = new HashMap<String, Set<String>>();
		Iterator<String> jsonIterator = allDerivations.keys();
		while (jsonIterator.hasNext()) {
			String derivation = jsonIterator.next();
			try {
				if (allDerivations.getJSONObject(derivation).has("flags")) {
					String flag = allDerivations.getJSONObject(derivation).getString("flags");
					if (derivations.get(flag) == null){
						derivations.put(flag, new HashSet<String>());	
					}
					derivations.get(flag).add(derivation);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return derivations;
	}
}
