package br.usp.ime.cogroo.model;

import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Test;

public class ParserYamlTest {

	@Test
	public void testeHashNivel1() {
		try {
			ParserYaml p = ParserYaml.getInstance();
			Assert.assertEquals("Categoria", p.getValue("CAT"));
			Assert.assertEquals("Semântica", p.getValue("SEM"));
			Assert.assertEquals("Número", p.getValue("N"));
			Assert.assertEquals("Abreviatura", p.getValue("ABR"));
			Assert.assertEquals("Origem brasileira", p.getValue("BRAS"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeHashNivel2() {
		try {
			ParserYaml p = ParserYaml.getInstance();
			Assert.assertEquals("obra literária", p.getValue("SEM", "livro"));
			Assert.assertEquals("interjeição", p.getValue("CAT", "in"));
			Assert.assertEquals("pronome pessoal", p.getValue("CAT", "ppes"));
			Assert.assertEquals("onde", p.getValue("Adv", "onde"));
			Assert.assertEquals("advérbio de lugar", p.getValue("SUBCAT", "lug"));
			Assert.assertEquals("abertura de parênteses", p.getValue("CAT", "punct2e"));
			Assert.assertEquals("pouco frequente", p.getValue("F", "0"));
			Assert.assertEquals("-idade", p.getValue("FSEM", "dd"));
			Assert.assertEquals("transitivo/intransitivo", p.getValue("TR", "_"));
			Assert.assertEquals("primeira", p.getValue("P", "1"));
			Assert.assertEquals("primeira/terceira", p.getValue("P", "1_3"));
			Assert.assertEquals("re-", p.getValue("PFSEM", "outra"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
 
	
}