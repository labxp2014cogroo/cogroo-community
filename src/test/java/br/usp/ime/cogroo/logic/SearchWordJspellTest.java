package br.usp.ime.cogroo.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Test;

public class SearchWordJspellTest {

	@Test
	public void testWordExistsInJspell() {
		try {
			Assert.assertEquals(true, SearchWordJspell.existsInJspell("casa"));
			Assert.assertEquals(true, SearchWordJspell.existsInJspell("bola"));
			Assert.assertEquals(false,
					SearchWordJspell.existsInJspell("bolaas"));
			Assert.assertEquals(false, SearchWordJspell.existsInJspell("if"));
			Assert.assertEquals(true,
					SearchWordJspell.existsInJspell("bolacha"));
			Assert.assertEquals(true,
					SearchWordJspell.existsInJspell("inconstitucional"));
			Assert.assertEquals(true, SearchWordJspell.existsInJspell("afinal"));
			Assert.assertEquals(true, SearchWordJspell.existsInJspell("imoral"));
			Assert.assertEquals(false,
					SearchWordJspell.existsInJspell("bolachão"));
		} catch (FileNotFoundException e) {
			// ignora o aruivo log
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void searchUnknownWords() throws IOException {
		LinkedList<String> expected = new LinkedList<String>();
		expected.add("BLaBLAbla");
		expected.add("feiia");
		Assert.assertEquals(expected,
				SearchWordJspell.searchUnknownWords("minha BLaBLAbla é feiia"));

		expected = new LinkedList<String>();
		Assert.assertEquals(
				expected,
				SearchWordJspell
						.searchUnknownWords("eu moro em Pinheiros, mas vou me mudar hoje!"));

		expected = new LinkedList<String>();
		expected.add("Pinheiroos");
		Assert.assertEquals(
				expected,
				SearchWordJspell
						.searchUnknownWords("eu moro em Pinheiroos, mas vou me mudar hoje!"));

		expected = new LinkedList<String>();
		expected.add("UNIP");
		expected.add("UNINOVE");
		expected.add("UNICID");
		Assert.assertEquals(
				expected,
				SearchWordJspell
						.searchUnknownWords("Olá! Onde você estuda? Na UNIP, UNINOVE; UNICID. ?"));
	}

}
