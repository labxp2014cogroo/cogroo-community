package br.usp.ime.cogroo.model;

import java.util.HashMap;

public class Vocable {
	
	public enum Category{
		PUNCTUATION,
		POSSESSIVE_PRONOUN,
		INDEFINITE_PRONOUN,
		DEMONSTRATIVE_PRONOUN,
		INTERROGATIVE_PRONOUN,
		PERSONAL_PRONOUN,
		RELATIVE_PRONOUN,
		PREPOSITION,
		INTERJECTION,
		SUBORDINATIVE_CONJUCTION,
		COORDINATIVE_CONJUCTION,
		COMMON_NOUN,
		PROPER_NOUN,
		ADJECTIVE,
		ADJECTIVE_NOUN,
		PASSIVE_PARTICLE,
		VERB,
		CONTRACTION,
		CARDINAL,
		ORDINAL,
		ADVERB
	};
	
	private String word;
	private Category category;
	private Vocable radical;
	private HashMap<String, String> properties;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public void setCategory(String category) {
		/* TODO:
		 * 
		 * 		switch case to convert string --> Category
		 * 
		 */
	}

	public Vocable getRadical() {
		return radical;
	}
	public void setRadical(Vocable radical) {
		this.radical = radical;
	}
	
	
}
