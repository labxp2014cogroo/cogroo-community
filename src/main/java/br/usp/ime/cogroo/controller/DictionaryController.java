package br.usp.ime.cogroo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.cogroo.dao.CogrooFacade;
import br.usp.ime.cogroo.logic.TextSanitizer;
import br.usp.ime.cogroo.model.LoggedUser;

@Resource
public class DictionaryController {

	private final Result result;

	public DictionaryController(Result result) {
		this.result = result;
	}

	@Path("/newEntry")
	public void newEntry() {
	
	}

	@Get
	@Path("/newEntry/{text*}")
	public void newEntryGET(String text) {

	}

	@Post
	@Path("/trataPalavra")
	public void trataPalavra(String word) {
		String mensagem = "";
		if (word == null) {
			mensagem = "ERRO!!!";
		}
		else if (word.length() == 0) {
			mensagem = "Nenhuma palavra foi digitada!";
		}
		else {
			//Tem que chamar o metodo de busca de palavras no jspell com word
			mensagem = "Vc digitou: " + word;
		}
		result.include("mensagem", mensagem).redirectTo(this).newEntry();
	}
}





