package br.usp.ime.cogroo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.usp.ime.cogroo.model.errorreport.Comment;
import br.usp.ime.cogroo.model.errorreport.HistoryEntry;
import br.usp.ime.cogroo.model.errorreport.State;

@Entity
public class DictionaryPatch {
	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "errorEntry", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creation;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	@OneToMany(mappedBy = "errorEntry", cascade = CascadeType.ALL)
	private List<HistoryEntry> historyEntries;

	private State state;

	private String newEntry;
	private String previousEntry;

	@Transient
	private boolean isNew = false;

	public DictionaryPatch() {
		this.isNew = true;
		this.state = State.OPEN;
	}

	public DictionaryPatch(List<Comment> comments, User user, Date creation,
			Date modified, State state, String newEntry, String previousEntry) {
		super();
		this.comments = comments;
		this.user = user;
		this.creation = creation;
		this.modified = modified;
		this.state = state;
		this.newEntry = newEntry;
		this.previousEntry = previousEntry;
	}

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	@Transient
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getNewEntry() {
		return newEntry;
	}

	public void setNewEntry(String newEntry) {
		this.newEntry = newEntry;
	}

	public String getPreviousEntry() {
		return previousEntry;
	}

	public void setPreviousEntry(String previousEntry) {
		this.previousEntry = previousEntry;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCommentCount() {
		int count = 0;
		for (Comment comment : getComments()) {
			count += comment.getCount();
		}
		return count;
	}

	@PrePersist
	protected void onCreate() {
		creation = modified = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		modified = new Date();
	}

	public List<HistoryEntry> getHistoryEntries() {
		return historyEntries;
	}

	public void setHistoryEntries(List<HistoryEntry> historyEntries) {
		this.historyEntries = historyEntries;
	}

}
