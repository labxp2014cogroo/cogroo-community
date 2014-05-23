package br.usp.ime.cogroo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.usp.ime.cogroo.dao.DictionaryPatchDAO;
import br.usp.ime.cogroo.logic.DerivationsQuery;
import br.usp.ime.cogroo.model.DictionaryPatch;
import br.usp.ime.cogroo.model.LoggedUser;

@Resource
public class DictionaryPatchController {
	
	private Result result;
	private DictionaryPatchDAO dictionaryPatchDAO;
	private LoggedUser loggedUser;
	
	public DictionaryPatchController(Result result,
			DictionaryPatchDAO dictionaryPatchDAO,
			LoggedUser loggedUser) {
		this.result = result;
		this.dictionaryPatchDAO = dictionaryPatchDAO;
		this.loggedUser = loggedUser;
	}

	@Get
	@Path("/getPatch")
	public void getPatchDetails(Long idPatch) throws JSONException{
		DictionaryPatch dictionaryPatch = dictionaryPatchDAO.retrieve(idPatch);
		JSONObject response = new JSONObject();
		try{
			response.append("ok", 0);
			if (dictionaryPatch == null){
				response.append("status", 1);
				response.append("msg", "Erro: o id passado não existe no banco");
				result.use(Results.http()).body(response.toString());
			}else {
				response.append("status", 0);
				response.append("msg", "OK");
				HashMap<String, Set<String>> derivationsHash = DerivationsQuery.getDerivationsFromFlags(dictionaryPatch.getNewEntry());
				JSONObject jsonDerivations = new JSONObject(derivationsHash); 
				
				response.append("derivations", jsonDerivations);
				response.append("tipo","inserção");
				
				// j.append("comentarios", new JSONObject(dictionaryPatch.getComments()));
				result.use(Results.http()).body(response.toString());
			}
		}catch(JSONException e){
			response.append("status", 2);
			response.append("msg", "Erro: houve algum problema ao tentar pegar as derivações (Webservice ?)");
		}
		result.use(Results.http()).body(response.toString());
	}
	
	
	@Path("/dictionaryEntries")
	public void dictionaryEntries() {
		List<DictionaryPatch> dictionaryPatchList = new ArrayList<DictionaryPatch>();
		
		dictionaryPatchList = dictionaryPatchDAO.retriveFromUser(loggedUser.getUser().getId());
		
		result.include("dictionaryPatchList", dictionaryPatchList);
	}
	
	@Get
	@Path("/dictionaryEntries/{patch.id}")
	public void entriesDetails(DictionaryPatch dictionaryPatch) {
		if (dictionaryPatch == null) {
			result.redirectTo(getClass()).dictionaryEntries();
			return;
		}
		
		result.include("dictionaryPatch", dictionaryPatch);	
	}
	
	
}