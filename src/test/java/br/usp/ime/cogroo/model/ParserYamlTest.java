package br.usp.ime.cogroo.model;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class ParserYamlTest {

	@Test
	public void testeHashNivel1() throws FileNotFoundException,
			UnsupportedEncodingException {
		ParserYaml p = ParserYaml.getInstance();
		assertEquals("Categoria", p.getValue("CAT"));
		assertEquals("Semântica", p.getValue("SEM"));
		assertEquals("Número", p.getValue("N"));
		assertEquals("Abreviatura", p.getValue("ABR"));
		assertEquals("Origem brasileira", p.getValue("BRAS"));
		assertEquals("Apontador para a palavra pré Acordo Ortográfico de 1990",
				p.getValue("PREAO90"));
	}

	@Test
	public void testeHashNivel2() throws FileNotFoundException,
			UnsupportedEncodingException {
		ParserYaml p = ParserYaml.getInstance();
		assertEquals("obra literária", p.getValue("SEM", "livro"));
		assertEquals("interjeição", p.getValue("CAT", "in"));
		assertEquals("pronome pessoal", p.getValue("CAT", "ppes"));
		assertEquals("onde", p.getValue("Adv", "onde"));
		assertEquals("advérbio de lugar", p.getValue("SUBCAT", "lug"));
		assertEquals("abertura de parênteses", p.getValue("CAT", "punct2e"));
		assertEquals("pouco frequente", p.getValue("F", "0"));
		assertEquals("-idade", p.getValue("FSEM", "dd"));
		assertEquals("transitivo/intransitivo", p.getValue("TR", "_"));
		assertEquals("primeira", p.getValue("P", "1"));
		assertEquals("primeira/terceira", p.getValue("P", "1_3"));
		assertEquals("infinitivo", p.getValue("T", "inf"));
		assertEquals("re-", p.getValue("PFSEM", "outra"));
	}

	@Test
	public void getValueTestShouldBeNull() throws FileNotFoundException,
			UnsupportedEncodingException {
		assertEquals(ParserYaml.getInstance().getValue("ThisIsNotAKey"), null);
	}

	@Test
	public void getValueCategoryShouldBeNull() throws FileNotFoundException,
			UnsupportedEncodingException {
		assertEquals(
				ParserYaml.getInstance().getValue("ThisIsNotACategory",
						"ThisIsNotAProperty"), null);
	}

	@Test
	public void getValuePropertyShouldBeNull() throws FileNotFoundException,
			UnsupportedEncodingException {
		assertEquals(
				ParserYaml.getInstance().getValue("N", "ThisIsNotAProperty"),
				null);
	}

}
