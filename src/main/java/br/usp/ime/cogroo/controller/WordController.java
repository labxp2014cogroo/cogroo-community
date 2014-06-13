package br.usp.ime.cogroo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.usp.ime.cogroo.dao.DictionaryPatchDAO;
import br.usp.ime.cogroo.exceptions.ExceptionMessages;
import br.usp.ime.cogroo.logic.DerivationsQuery;
import br.usp.ime.cogroo.logic.SearchWordJspell;
import br.usp.ime.cogroo.model.DictionaryPatch;
import br.usp.ime.cogroo.model.Flags;
import br.usp.ime.cogroo.model.LoggedUser;
import br.usp.ime.cogroo.model.Vocable;

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
