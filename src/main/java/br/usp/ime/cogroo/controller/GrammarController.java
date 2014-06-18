package br.usp.ime.cogroo.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.jfree.util.Log;
import org.json.JSONException;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.usp.ime.cogroo.dao.CogrooFacade;
import br.usp.ime.cogroo.logic.SearchWordJspell;
import br.usp.ime.cogroo.logic.TextSanitizer;
import br.usp.ime.cogroo.model.LoggedUser;

/**
 * Today this is the entry point of the web application. It shows a form where a
 * user can enter a text to be analyzed.
 * 
 */
@Resource
public class GrammarController {

	private final Result result;
	private CogrooFacade cogroo;
	private LoggedUser loggedUser;
	private Validator validator;
	private TextSanitizer sanitizer;

	private static final ResourceBundle messages = ResourceBundle.getBundle(
			"messages", new Locale("pt_BR"));

	public GrammarController(Result result, CogrooFacade cogroo,
			LoggedUser loggedUser, Validator validator, TextSanitizer sanitizer) {
		this.result = result;
		this.cogroo = cogroo;
		this.loggedUser = loggedUser;
		this.validator = validator;
		this.sanitizer = sanitizer;
	}

	@Get
	@Path("/grammar")
	public void grammar() {
		if (!result.included().containsKey("text")) {
			String exampleText = "Isto é um exemplo de erro gramaticais.";
			result.include("text", exampleText);
			grammar(exampleText);
		}
		result.include("headerTitle", messages.getString("GRAMMAR_HEADER"))
				.include("headerDescription",
						messages.getString("GRAMMAR_DESCRIPTION"));
	}

	@Get
	@Path("/grammar/{text*}")
	public void grammarGET(String text) {
		if (text != null) {
			grammar(text);
			result.redirectTo(getClass()).grammar();
			return;
		}
	}

	@Post
	@Path("/grammar")
	public void grammar(String text) {
		text = sanitizer.sanitize(text, false, true);
		if (text != null && text.length() > 0) {
			if (text.length() > 255) {
				text = text.substring(0, 255);
			}
			result.include("justAnalyzed", true);
			if (loggedUser.isLogged())
				result.include("gaEventGrammarAnalyzed", true).include(
						"provider", loggedUser.getUser().getService());
			else
				result.include("gaEventGrammarAnalyzed", true).include(
						"provider", "anonymous");

			result.include("processResultList", cogroo.processText(text))
					.include("text", text);
		}
		result.include("headerTitle", messages.getString("GRAMMAR_HEADER"))
				.include("headerDescription",
						messages.getString("GRAMMAR_DESCRIPTION"));

		try {
			List<String> unknownWords = SearchWordJspell
					.searchUnknownWords(text);
			result.include("unknownWordsList", unknownWords);
		} catch (IOException e) {
			Log.error("Falhou ao consultar as palavras existentes.", e);
			result.include("unknownWordsList", new LinkedList<String>());
		} catch (JSONException e) {
			Log.error("Falhou ao consultar as palavras existentes.", e);
		}
	}

}