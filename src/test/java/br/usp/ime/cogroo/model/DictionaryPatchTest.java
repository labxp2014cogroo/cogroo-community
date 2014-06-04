package br.usp.ime.cogroo.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.mchange.util.AssertException;

import utils.LabXP2014;
import br.usp.ime.cogroo.model.errorreport.Comment;
import br.usp.ime.cogroo.model.errorreport.State;

@Category(LabXP2014.class)
public class DictionaryPatchTest {

	@Test
	public void testDictonaryPatch() {

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

		DictionaryPatch sample = new DictionaryPatch(comments, user, creation,
				modified, state, newEntry, previousEntry);

		assertEquals(comments.get(0), sample.getComments().get(0));
		assertEquals(user, sample.getUser());
		assertEquals(creation, sample.getCreation());
		assertEquals(modified, sample.getModified());
		assertEquals(state, sample.getState());
		assertEquals(newEntry, sample.getNewEntry());
		assertEquals(previousEntry, sample.getPreviousEntry());
		assertEquals(1, sample.getCommentCount());

	}

	@Test
	public void dictionaryPatchConstructorTest() {
		DictionaryPatch patch = new DictionaryPatch();
		assertEquals(patch.getIsNew(), true);
		assertEquals(patch.getState(), State.OPEN);
	}

	@Test
	public void setIsNewTest() {
		DictionaryPatch patch = new DictionaryPatch();
		patch.setIsNew(false);
		assertEquals(patch.getIsNew(), false);
		patch.setIsNew(true);
		assertEquals(patch.getIsNew(), true);
	}

	@Test
	public void commentsTest() {
		DictionaryPatch patch = new DictionaryPatch();
		List<Comment> commentsList = new Vector<Comment>();
		commentsList.add(new Comment());
		patch.setComments(commentsList);
		assertEquals(patch.getComments(), commentsList);
	}

	@Test
	public void userTest() {
		DictionaryPatch patch = new DictionaryPatch();
		User user = new User();
		patch.setUser(user);
		assertEquals(patch.getUser(), user);
	}

	@Test
	public void modificationDateTest() {
		DictionaryPatch patch = new DictionaryPatch();
		Date modified = new Date();
		patch.setModified(modified);
		assertEquals(patch.getModified(), modified);

	}

}
