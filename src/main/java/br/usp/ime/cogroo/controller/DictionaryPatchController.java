package br.usp.ime.cogroo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.cogroo.dao.DictionaryPatchDAO;
import br.usp.ime.cogroo.model.DictionaryPatch;

@Resource
public class DictionaryPatchController {
	
	private Result result;
	private DictionaryPatchDAO dictionaryPatchDAO;
	
	
	
	public DictionaryPatchController(Result result,
			DictionaryPatchDAO dictionaryPatchDAO) {
		this.result = result;
		this.dictionaryPatchDAO = dictionaryPatchDAO;
	}

	@Path("/entries")
	public void entriesList() {
		
	}
	
	@Get
	@Path("/entries/{patch.id}")
	public void entriesDetails(DictionaryPatch dictionaryPatch) {
		if (dictionaryPatch == null) {
			result.redirectTo(getClass()).entriesList();
			return;
		}
		
		DictionaryPatch dictionaryPatchFromDB = dictionaryPatchDAO.retrieve(new Long(dictionaryPatch.getId()));
		
		if (dictionaryPatchFromDB == null) {
			result.notFound();
			return;
		}
		
		result.include("dictionaryPatch", dictionaryPatch);	
	}
	
	
}