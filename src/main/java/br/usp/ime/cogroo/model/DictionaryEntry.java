package br.usp.ime.cogroo.model;

public class DictionaryEntry {

	private String gender;
	private String number;
	private String verbtense;
	private Category category;
	
	public enum Category {
		COMMON_NOUN,
		PROPER_NOUN,
		VERB,
		ADJECTIVE,
		OTHER,
	};
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getVerbtense() {
		return verbtense;
	}

	public void setVerbtense(String verbtense) {
		this.verbtense = verbtense;
	};
	
	
	
	
	
}
