package br.usp.ime.cogroo.model;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class FlagsTest {

	@Test
	public void testGetTextFromFlag() {
		try {
			Flags flags = Flags.getInstance();
			System.out.println(flags.getTextFromFlag("a"));
			Assert.assertEquals("O plural é da forma mão -> mãos", flags.getTextFromFlag("a"));
			Assert.assertEquals("Aceita o prefixo \"anti-\"", flags.getTextFromFlag("A"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
