package br.usp.ime.cogroo.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import br.usp.ime.cogroo.controller.RuleController;

public class DerivationsQuery {
	static final String ALL_FLAGS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static final String VERB_FLAGS = "BCDEIKLMNOPRSWXYZcdinouv";
	static final String ADJECTIVE_FLAGS = "AEFGHIORSabcdfhijmnpstuvxz";
	static final String NOUN_FLAGS = "AEFGIORSTVabdefghijpqrstuvwxyz";
	static final String ADVERB_FLAGS = "h";

	private static final Logger LOG = Logger.getLogger(RuleController.class);

	private DerivationsQuery() {
		return;
	}

	private static String selectFlags(String category) {
		if (category.equals("v"))
			return VERB_FLAGS;
		if (category.equals("adj"))
			return ADJECTIVE_FLAGS;
		if (category.equals("nc") || category.equals("np"))
			return NOUN_FLAGS;
		if (category.equals("adv"))
			return ADVERB_FLAGS;
		return ALL_FLAGS;
	}

	public static Map<String, String> queryDerivations(String entry,
			String category) {
		Map<String, String> derivationsHash = null;
		try {
			JSONObject jsonResult = WebServiceProxy.getInstance()
					.tryRequest(entry + selectFlags(category))
					.getJSONObject("derivadas");
			derivationsHash = getRelevantDerivations(jsonResult);
		} catch (IOException e) {
			Log.error("Não foi possivel completar o pedido", e);
		} catch (JSONException e) {
			Log.error("Erro na codificação do JSON", e);
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
				Log.error("Erro na codificação do JSON", e);
			}
		}

		return relevantDerivations;
	}

	public static Map<String, Set<String>> getDerivationsFromFlags(String entry) {

		Map<String, Set<String>> derivationsHash = null;

		try {
			JSONObject jsonResult = WebServiceProxy.getInstance()
					.tryRequest(entry).getJSONObject("derivadas");
			derivationsHash = fillHashMapDerivations(jsonResult);
		} catch (IOException e) {
			Log.error("Não foi possivel completar o pedido", e);
		} catch (JSONException e) {
			Log.error("Erro na codificação do JSON", e);
		}
		return derivationsHash;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Set<String>> fillHashMapDerivations(
			JSONObject allDerivations) {

		Map<String, Set<String>> derivations = new HashMap<String, Set<String>>();
		Iterator<String> jsonIterator = allDerivations.keys();
		while (jsonIterator.hasNext()) {
			String derivation = jsonIterator.next();
			try {
				if (allDerivations.getJSONObject(derivation).has("flags")) {
					String flag = allDerivations.getJSONObject(derivation)
							.getString("flags");
					if (derivations.get(flag) == null) {
						derivations.put(flag, new HashSet<String>());
					}
					derivations.get(flag).add(derivation);
				}
			} catch (JSONException e) {
				Log.error("Erro na codificação do JSON", e);
			}
		}

		return derivations;
	}
}
