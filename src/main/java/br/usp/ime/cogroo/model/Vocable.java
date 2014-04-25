package br.usp.ime.cogroo.model;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Vocable {
	
	private String word;
	private String radical;
	private String category;
	private LinkedList<String> properties;
	private ParserYaml parser;
	
	public Vocable() {
		try {
			parser = ParserYaml.getInstance();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Vocable(String category, String word, String radical) {
		this.word = word;
		this.radical = radical;
		this.properties = new LinkedList<String>();
		
		try {
			parser = ParserYaml.getInstance();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.category = parser.getValue("CAT", category); 
		
	}
	
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String category) {
		this.category = parser.getValue("CAT", category);
	}
	
	
	public String getPropertiesAsString() {
		
		StringBuilder description = new StringBuilder();
		
		for (String property : this.properties){
			description.append(property).append(", ");
		}
		
		return description.toString();
	}
	
	public void addProperty (String key, String value) {
		if (parser.getValue(key, value) != null){
			this.properties.add(parser.getValue(key, value));
		}
	}

	public String getRadical() {
		return radical;
	}
	public void setRadical(String radical) {
		this.radical = radical;
	}
	
	
}
