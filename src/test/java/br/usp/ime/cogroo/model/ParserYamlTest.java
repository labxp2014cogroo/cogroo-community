package br.usp.ime.cogroo.model;

import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Test;

public class ParserYamlTest {

	@Test
	public void shouldBeEqualsIfWordisEqual() {
		try {
			ParserYaml p = ParserYaml.getInstance();
			Assert.assertEquals("Categoria", p.getValue("CAT"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
