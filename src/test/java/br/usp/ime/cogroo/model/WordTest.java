package br.usp.ime.cogroo.model;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;

@Category(LabXP2014.class)
public class WordTest {

	@Test
	public void shouldBeEqualsIfWordisEqual() {
		Word word = new Word();
		word.setWord("palavra");

		Word expected = new Word();
		expected.setWord("palavra");

		Assert.assertEquals(expected, word);
	}
}
