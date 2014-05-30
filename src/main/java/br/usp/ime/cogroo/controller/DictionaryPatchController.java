package br.usp.ime.cogroo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
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
			response.put("ok", 0);
			if (dictionaryPatch == null){
				response.put("status", 1);
				response.put("msg", "Erro: o id passado não existe no banco");
				result.use(Results.http()).body(response.toString());
			}else {
				response.put("status", 0);
				response.put("msg", "OK");
				HashMap<String, Set<String>> derivationsHash = DerivationsQuery.getDerivationsFromFlags(dictionaryPatch.getNewEntry());
				JSONObject jsonDerivations = new JSONObject(derivationsHash); 
				
				response.put("derivations", jsonDerivations);
				response.put("tipo","inserção");
			}
		}catch(JSONException e){
			response.put("status", 2);
			response.put("msg", "Erro: houve algum problema ao tentar pegar as derivações (Webservice ?)");
		}
		result.use(Results.http()).body(response.toString());
	}
	
	@Get
	@Path("/dictionaryEntries")
	public void dictionaryEntries() {
		List<DictionaryPatch> dictionaryPatchList = new ArrayList<DictionaryPatch>();
		
		if (loggedUser.getUser().getRole().getRoleName().equals("user")) {
			dictionaryPatchList = dictionaryPatchDAO.retriveFromUser(loggedUser.getUser().getId());
		}
		else {
			dictionaryPatchList = dictionaryPatchDAO.retrieveAll();
		}
		result.include("dictionaryPatchList", dictionaryPatchList);
	}
	
	@Post
	@Path("/patchApproval")
	public void patchApproval(String[] flags, long idPatch){
		DictionaryPatch dictionaryPatch = dictionaryPatchDAO.retrieve(idPatch);
		
		String newEntry = dictionaryPatch.getNewEntry().substring(0, dictionaryPatch.getNewEntry().lastIndexOf("/")+1);
		
		if (flags != null) {
			for (String flag : flags) {
				newEntry += flag;
			}
		}
		
		// TODO jogar a newEntry no WebService 
		System.out.println(newEntry);
		
		result.redirectTo(getClass()).dictionaryEntries();
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