package br.usp.ime.cogroo.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class DictionaryPatchTest {

	@Test
	public void testDictonaryPatch() {
		String testComment = "a comment";
		Short approved = 0;
		String oldEntry = "old";
		String newEntry = "new";
		User user = new User("ricardo");
		
		DictionaryPatch sample = new DictionaryPatch(testComment, approved, user, newEntry, oldEntry);
		
		assertEquals(testComment, sample.getComment());
		assertEquals(approved, sample.getApproved());
		assertEquals(oldEntry, sample.getPreviousEntry());
		assertEquals(newEntry, sample.getEntry());
		assertEquals(user, sample.getUser());
		
	}

}
