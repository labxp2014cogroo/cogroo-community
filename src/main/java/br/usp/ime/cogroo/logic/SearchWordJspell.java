package br.usp.ime.cogroo.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.usp.ime.cogroo.model.Vocable;

public class SearchWordJspell {

	public static List<Vocable> searchWord(String word) throws IOException,
			JSONException {
		List<Vocable> vocables = new LinkedList<Vocable>();
		JSONArray analisis = WebServiceProxy.getInstance()
				.analysisRequest(word).getJSONArray("analise");
		for (int i = 0; i < analisis.length(); i++) {
			JSONObject json = analisis.getJSONObject(i);
			Vocable vocable = WebServiceProxy.dictionaryEntryJsonToVocable(
					json, word);
			vocables.add(vocable);
		}
		return vocables;
	}

	public static HashMap<String, Vocable> searchLemma(String text)
			throws IOException, JSONException {
		HashMap<String, Vocable> vocables = new HashMap<String, Vocable>();
		WebServiceProxy webServiceProxy = WebServiceProxy.getInstance();
		JSONArray analysis = webServiceProxy.analysisRequest(text)
				.getJSONArray("analise");
		// Iterates over each radical (lemma)
		for (int i = 0; i < analysis.length(); i++) {
			JSONObject analysisJSON = analysis.getJSONObject(i);
			String lemma = analysisJSON.getString("rad");
			JSONObject retrieveResult = webServiceProxy.retrieveRequest(lemma);
			Iterator<String> dictionariesIterator = retrieveResult.keys();
			// Iterates over each category of dictionary
			while (dictionariesIterator.hasNext()) {
				JSONArray entries = retrieveResult
						.getJSONArray(dictionariesIterator.next());
				// Iterates over each entry
				for (int j = 0; j < entries.length(); j++) {
					String entry = entries.getString(j);
					JSONObject lemmaResult = webServiceProxy.tryRequest(lemma);
					Vocable v = WebServiceProxy.dictionaryEntryJsonToVocable(lemmaResult, lemma);
					vocables.put(entry, v);
				}
			}
		}
		return vocables;
	}

	public static List<String> searchUnknownWords(String text)
			throws IOException, JSONException {

		List<String> unknownWords = new LinkedList<String>();
		StringTokenizer tokens = new StringTokenizer(text, " ,.!?();:");
		String token;

		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (!existsInJspell(token)) {
				unknownWords.add(token);
			}
		}

		return unknownWords;
	}

	public static boolean existsInJspell(String word) throws IOException,
			JSONException {
		return (!SearchWordJspell.searchWord(word).isEmpty());
	}

}
