package br.usp.ime.cogroo.model;

public class Vocable {
	public enum Category{
		COMMON_NOUN,
		PROPER_NOUN,
		VERB,
		ADJECTIVE,
		CARDINAL,
		ORDINAL,
		PERSONAL_PRONOUN,
		RELATIVE_PRONOUN,
		POSSESSIVE_PRONOUN,
		DEMONSTRATIVE_PRONOUN,
		INTERROGATIVE_PRONOUN,
		INDEFINITE_PRONOUN
	};
	
	private String word;
	private Category category;
	private Vocable radical;
	
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
	public Vocable getRadical() {
		return radical;
	}
	public void setRadical(Vocable radical) {
		this.radical = radical;
	}
	
	
}
