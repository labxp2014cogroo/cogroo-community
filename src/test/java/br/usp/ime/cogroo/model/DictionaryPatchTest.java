package br.usp.ime.cogroo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;
import br.usp.ime.cogroo.model.errorreport.Comment;
import br.usp.ime.cogroo.model.errorreport.HistoryEntry;
import br.usp.ime.cogroo.model.errorreport.State;

@Category(LabXP2014.class)
public class DictionaryPatchTest {

	private final double DELTA = 1e-8;
	private DictionaryPatch patch = new DictionaryPatch();

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
		assertEquals(patch.getIsNew(), true);
		assertEquals(patch.getState(), State.OPEN);
	}

	@Test
	public void setIsNewTest() {
		patch.setIsNew(false);
		assertEquals(patch.getIsNew(), false);
		patch.setIsNew(true);
		assertEquals(patch.getIsNew(), true);
	}

	@Test
	public void commentsTest() {
		List<Comment> commentsList = new Vector<Comment>();
		commentsList.add(new Comment());
		patch.setComments(commentsList);
		assertEquals(patch.getComments(), commentsList);
	}

	@Test
	public void userTest() {
		User user = new User();
		patch.setUser(user);
		assertEquals(patch.getUser(), user);
	}

	@Test
	public void modificationDateTest() {
		Date modified = new Date();
		patch.setModified(modified);
		assertEquals(patch.getModified(), modified);

	}

	@Test
	public void setCreationTest() {
		Date creation = new Date();
		patch.setCreation(creation);
		assertEquals(patch.getCreation(), creation);
	}

	@Test
	public void setStateTest() {
		patch.setState(State.OPEN);
		assertEquals(patch.getState(), State.OPEN);
		patch.setState(State.INPROGRESS);
		assertEquals(patch.getState(), State.INPROGRESS);
		patch.setState(State.RESOLVED);
		assertEquals(patch.getState(), State.RESOLVED);
		patch.setState(State.FEEDBACK);
		assertEquals(patch.getState(), State.FEEDBACK);
	}

	@Test
	public void setNewEntryTest() {
		String newEntry = "NewEntryTest";
		patch.setNewEntry(newEntry);
		assertEquals(patch.getNewEntry(), newEntry);
	}

	@Test
	public void setPreviousEntryTest() {
		String previousEntry = "PreviousEntryTest";
		patch.setPreviousEntry(previousEntry);
		assertEquals(patch.getPreviousEntry(), previousEntry);
	}

	@Test
	public void setAndGetIdTest() {
		patch.setId(10L);
		assertEquals(patch.getId(), 10L, DELTA);
	}

	@Test
	public void onCreateTest() {
		patch.onCreate();
		assertEquals(patch.getCreation(), patch.getModified());
	}

	@Test
	public void onUpdateModifiedDateIsDate() {
		patch.onUpdate();
		assertTrue(patch.getModified() instanceof Date);
	}

	@Test
	public void setAndGetHistoryEntriesTest() {
		List<HistoryEntry> historyEntries = new Vector<HistoryEntry>();
		patch.setHistoryEntries(historyEntries);
		assertEquals(patch.getHistoryEntries(), historyEntries);
	}

}
