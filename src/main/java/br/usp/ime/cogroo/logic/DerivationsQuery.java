package br.usp.ime.cogroo.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class DerivationsQuery {
	static final String FLAGS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private DerivationsQuery() {
		return;
	}

	/**
	 * 
	 * @param text
	 *            é um paramêtro do formato radical/classificação/
	 * @return um HashMap que associa uma flag à palavra gerada
	 */

	public static Map<String, String> queryDerivations(String text) {
		Map<String, String> derivationsHash = null;
		try {
			JSONObject jsonResult = WebServiceProxy.getInstance().tryRequest(text + FLAGS).getJSONObject("derivadas");
			derivationsHash = getRelevantDerivations(jsonResult);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return derivationsHash;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getRelevantDerivations(
			JSONObject allDerivations) {
		Map<String, String> relevantDerivations = new HashMap<String, String>();
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

	public static Map<String, Set<String>> getDerivationsFromFlags(
			String entry) {

		Map<String, Set<String>> derivationsHash = null;

		try {
			JSONObject jsonResult = WebServiceProxy.getInstance().tryRequest(entry)
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
	private static Map<String, Set<String>>
			fillHashMapDerivations(	JSONObject allDerivations) {
		
		Map<String, Set<String>> derivations = new HashMap<String, Set<String>>();
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
