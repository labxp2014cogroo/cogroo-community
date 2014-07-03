package br.usp.ime.cogroo.logic;

import java.io.IOException;
import java.util.LinkedList;

import junit.framework.Assert;

import org.json.JSONException;
import org.junit.Test;

import br.usp.ime.cogroo.model.Vocable;

public class SearchWordJspellTest {

	@Test
	public void testWordExistsInJspell() throws JSONException, IOException {
		Assert.assertEquals(true, SearchWordJspell.existsInJspell("casa"));
		Assert.assertEquals(true, SearchWordJspell.existsInJspell("bola"));
		Assert.assertEquals(false, SearchWordJspell.existsInJspell("bolaas"));
		Assert.assertEquals(false, SearchWordJspell.existsInJspell("if"));
		Assert.assertEquals(true, SearchWordJspell.existsInJspell("bolacha"));
		Assert.assertEquals(true,
				SearchWordJspell.existsInJspell("inconstitucional"));
		Assert.assertEquals(true, SearchWordJspell.existsInJspell("afinal"));
		Assert.assertEquals(true, SearchWordJspell.existsInJspell("imoral"));
		Assert.assertEquals(false, SearchWordJspell.existsInJspell("bolachão"));
	}

	@Test
	public void testSearchLemma() throws JSONException, IOException {
		Vocable casar = new Vocable("v", "casar", "casar");
		casar.setEntry("casar/#vt/XYPLSMv/");
		casar.addProperty("T", "inf");
		casar.addProperty("TR", "t");

		Vocable casar2 = SearchWordJspell.searchLemma("casar").get(0);

		Assert.assertEquals(casar.getWord(), casar2.getWord());
		Assert.assertEquals(casar.getCategory(), casar2.getCategory());
		Assert.assertEquals(casar.getEntry(), casar2.getEntry());
		Assert.assertEquals(casar.getRadical(), casar2.getRadical());
		Assert.assertEquals(casar.getPropertiesAsString(),
				casar2.getPropertiesAsString());

		Vocable casa = new Vocable("nc", "casa", "casa");
		casa.setEntry("casa/#nf/p/");
		casa.addProperty("G", "f");
		casa.addProperty("N", "s");

		Vocable casa2 = SearchWordJspell.searchLemma("casa").get(1);

		Assert.assertEquals(casa.getWord(), casa2.getWord());
		Assert.assertEquals(casa.getCategory(), casa2.getCategory());
		Assert.assertEquals(casa.getEntry(), casa2.getEntry());
		Assert.assertEquals(casa.getRadical(), casa2.getRadical());
		Assert.assertEquals(casa.getPropertiesAsString(),
				casa2.getPropertiesAsString());

	}

	@Test
	public void searchUnknownWords() throws IOException, JSONException {
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
