package br.usp.ime.cogroo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.client.json.Json;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.usp.ime.cogroo.dao.DictionaryPatchDAO;
import br.usp.ime.cogroo.logic.DerivationsQuery;
import br.usp.ime.cogroo.model.DictionaryPatch;
import br.usp.ime.cogroo.model.User;
import br.usp.ime.cogroo.model.errorreport.Comment;
import br.usp.ime.cogroo.model.errorreport.State;

@Resource
public class DictionaryPatchController {
	
	private Result result;
	private DictionaryPatchDAO dictionaryPatchDAO;

	
	public DictionaryPatchController(Result result,
			DictionaryPatchDAO dictionaryPatchDAO) {
		this.result = result;
		this.dictionaryPatchDAO = dictionaryPatchDAO;
	}

	@Get
	@Path("/getPatch")
	public void getPatchDetails(Long idPatch) throws JSONException{
		DictionaryPatch dictionaryPatch = dictionaryPatchDAO.retrieve(idPatch);
		JSONObject response = new JSONObject();
		try {
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
		}
			catch (IOException e) {
			response.append("status", 2);
			response.append("msg", "Erro: houve algum problema ao tentar pegar as derivações (Webservice ?)");
		}
		result.use(Results.http()).body(response.toString());
	}
	
	
	@Path("/entries")
	public void entriesList() {
		
		List<DictionaryPatch> dictionaryPatchList = new ArrayList<DictionaryPatch>();
		
		User user = new User("ricardo");
		Date creation = new Date(0);
		Date modified = new Date(1000);
		State state = State.OPEN;
		String newEntry = "new";
		String previousEntry = "palavra";
		
		Comment comment = new Comment();
		comment.setComment("a comment");
		
		List<Comment> comments = new ArrayList<Comment>();
		comments.add(comment);
		
		DictionaryPatch sample = new DictionaryPatch(comments, user, creation, modified, state, newEntry, previousEntry);
		sample.setId(1L);

		dictionaryPatchList.add(sample);
		previousEntry = "ttpalavra";
		sample = new DictionaryPatch(comments, user, creation, modified, state, newEntry, previousEntry);
		sample.setId(2L);
		dictionaryPatchList.add(sample);
		
		
		
		result.include("dictionaryPatchList", dictionaryPatchList);
	}
	
	@Get
	@Path("/entries/{patch.id}")
	public void entriesDetails(DictionaryPatch dictionaryPatch) {
		if (dictionaryPatch == null) {
			result.redirectTo(getClass()).entriesList();
			return;
		}
		
		result.include("dictionaryPatch", dictionaryPatch);	
	}
	
	
}