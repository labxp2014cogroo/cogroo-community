package br.usp.ime.cogroo.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

public class DictionaryController {
	
	private final Result result;
	
	private static final ResourceBundle messages =
		      ResourceBundle.getBundle("messages", new Locale("pt_BR"));
	
	public DictionaryController(Result result) {
		this.result = result;
	}
	
	
	@Get
	@Path("/dictionary")
	public void newEntry () {
		
		result.include("headerTitle", messages.getString("GRAMMAR_HEADER"))
				.include("headerDescription",
						messages.getString("GRAMMAR_DESCRIPTION"));
	}
}
