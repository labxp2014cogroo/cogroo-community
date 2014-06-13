package br.usp.ime.cogroo.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;

@Category(LabXP2014.class)
public class VocableTest {
	
	private Vocable vocable = new Vocable();  

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
	
	@Test
	public void setAndGetWordTest() {
		String word = "WordTest"; 
		vocable.setWord(word);
		assertEquals(vocable.getWord(), word);
	}

	@Test 
	public void setAndGetRadical() {
		String radical = "RadicalTest"; 
		vocable.setRadical(radical);
		assertEquals(vocable.getRadical(), radical);
	}
	
	@Test
	public void setAndGetCategory() {
		vocable.setCategory("v");
		assertEquals(vocable.getCategory(), "verbo");
		vocable.setCategory("nc");
		assertEquals(vocable.getCategory(), "substantivo comum");
	}
	
	@Test
	public void addInvalidPropertyTest() {
		String old = vocable.getPropertiesAsString(); 
		vocable.addProperty("ThisIsNotAKey", "ThisIsNotAValue");
		String actual = vocable.getPropertiesAsString(); 
		assertEquals(old, actual);
	}

	@Test
	public void addValidPropertyTest() { 
		vocable.addProperty("T", "p");
		String actual = vocable.getPropertiesAsString(); 
		assertEquals("presente", actual);
	}
}
