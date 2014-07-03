package br.usp.ime.cogroo.model;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;

public class FlagsTest {

	@Test
	public void testGetTextFromFlag() {
		try {
			Flags flags = Flags.getInstance();
			System.out.println(flags.getTextFromFlag("a"));
			Assert.assertEquals("O plural é da forma mão -> mãos",
					flags.getTextFromFlag("a"));
			Assert.assertEquals("Aceita o prefixo \"anti-\"",
					flags.getTextFromFlag("A"));
			Flags.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
