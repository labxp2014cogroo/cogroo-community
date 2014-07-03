package br.usp.ime.cogroo.controller;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.usp.ime.cogroo.dao.DictionaryPatchDAO;
import br.usp.ime.cogroo.model.LoggedUser;

@Resource
public class WordController {

	private DictionaryPatchDAO dictionarypatchdao;
	private Result result;
	private Validator validator;
	private LoggedUser loggedUser;
	private HttpServletRequest request;

	public WordController(Result result, Validator validator,
			LoggedUser loggedUser, HttpServletRequest request,
			DictionaryPatchDAO dictionarypatchdao) {
		this.result = result;
		this.validator = validator;
		this.loggedUser = loggedUser;
		this.request = request;
		this.dictionarypatchdao = dictionarypatchdao;
	}
}
