package br.usp.ime.cogroo.logic;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.usp.ime.cogroo.model.Vocable;

public class SearchWordJspell {

	static final String baseAnalyseURL = "http://interno.cogroo.org:4040/jspell/analyse.json?id=default&lexeme=";

	public static List<Vocable> searchWord(String text) throws IOException, JSONException {
		List<Vocable> vocables = new LinkedList<Vocable>();
		JSONArray analisis = WebServiceProxy.getInstance()
				.analysisRequest(text).getJSONArray("analise");

		for (int i = 0; i < analisis.length(); i++) {
			JSONObject json = analisis.getJSONObject(i);
			Vocable v = new Vocable(json.getString("CAT"), text,
					json.getString("rad"));

			Iterator<String> jsonIterator = json.keys();
			while (jsonIterator.hasNext()) {
				String key = jsonIterator.next();
				if (!(key.equals("CAT") || key.equals("rad") || key
						.equals("PREAO90"))) {
					v.addProperty(key, json.getString(key));
				}
			}
			vocables.add(v);
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

	public static boolean existsInJspell(String word) throws IOException, JSONException {
		return (!SearchWordJspell.searchWord(word).isEmpty());
	}

}
