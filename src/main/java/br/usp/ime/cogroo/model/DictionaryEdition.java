package br.usp.ime.cogroo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DictionaryEdition {
	@Id
	@GeneratedValue
	private Long id;

	private String comment;

	private Short approved;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String newEntry;
	
	private String previousEntry;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Short getApproved() {
		return approved;
	}

	public void setApproved(Short approved) {
		this.approved = approved;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEntry() {
		return newEntry;
	}

	public void setEntry(String entry) {
		this.newEntry = entry;
	}

	public String getPreviousEntry() {
		return previousEntry;
	}

	public void setPreviousEntry(String previousEntry) {
		this.previousEntry = previousEntry;
	}

}
