package br.usp.ime.cogroo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.usp.ime.cogroo.dao.DictionaryPatchDAO;
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
		
//		DictionaryPatch dictionaryPatchFromDB = dictionaryPatchDAO.retrieve(new Long(dictionaryPatch.getId()));
//		
//		if (dictionaryPatchFromDB == null) {
//			result.notFound();
//			return;
//		}
		
		result.include("dictionaryPatch", dictionaryPatch);	
	}
	
	
}