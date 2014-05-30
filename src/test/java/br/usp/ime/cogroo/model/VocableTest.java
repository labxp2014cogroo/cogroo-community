package br.usp.ime.cogroo.model;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;

@Category(LabXP2014.class)
public class VocableTest {

	@Test
	public void testVocable() {
		String categoria = "nc";
		String word = "word";
	    String radical = "radical";
	    
		Vocable v = new Vocable(categoria, word, radical);
		
		assertEquals(v.getCategory(), "substantivo comum");
		assertEquals(v.getWord(), word);
		assertEquals(v.getRadical(), radical);
	}
	
	@Test
	public void testProperties() {
		String key = "key";
		String value = "value";
		String categoria = "nc";
		String word = "word";
	    String radical = "radical";
	    
		Vocable v = new Vocable(categoria, word, radical);
		v.addProperty(key, value);
		System.out.println(v.getPropertiesAsString());
		
		assertEquals(value + ", ", "value, ");
		
	}
}
