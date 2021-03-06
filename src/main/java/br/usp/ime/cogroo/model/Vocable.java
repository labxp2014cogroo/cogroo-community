package br.usp.ime.cogroo.model;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import br.usp.ime.cogroo.model.errorreport.ErrorEntry;

import com.google.common.base.Joiner;

public class Vocable {
	private static final Logger LOG = Logger.getLogger(ErrorEntry.class);

	private String word;
	private String radical;
	private String category;
	private LinkedList<String> properties;
	private ParserYaml parser;
	private String entry = "";
	private static final Joiner COMMA_JOINER = Joiner.on(", ");
	private boolean isGuess = false;

	public Vocable() {
		try {
			parser = ParserYaml.getInstance();
			this.properties = new LinkedList<String>();
		} catch (FileNotFoundException e) {
			LOG.error("File not found: " + ParserYaml.YAML_FILE);
		} catch (UnsupportedEncodingException e) {
			LOG.error("Unsupported Encoding: UTF-8 when trying to open "
					+ ParserYaml.YAML_FILE);
		}
	}

	public boolean equals(Vocable v) {
		return (this.word.equals(v.getWord())
				&& this.radical.equals(v.getRadical())
				&& this.entry.equals(v.getEntry()) && this.compareProperties(v));
	}

	private boolean compareProperties(Vocable v) {
		if (v == null) {
			return false;
		}

		if (this.properties.size() != v.properties.size()) {
			return false;
		}

		List<String> self = new ArrayList<String>(this.getProperties());
		List<String> other = new ArrayList<String>(v.getProperties());

		Collections.sort(self);
		Collections.sort(other);
		return self.equals(other);
	}

	public Vocable(String category, String word, String radical) {
		this();
		this.word = word;
		this.radical = radical;
		this.setCategory(category);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = parser.getValue("CAT", category);
	}

	public List<String> getProperties() {
		return this.properties;
	}

	public String getPropertiesAsString() {
		return COMMA_JOINER.join(this.properties.iterator());
	}

	public void addProperty(String key, String value) {
		if (parser.getValue(key, value) != null) {
			this.properties.add(parser.getValue(key, value));
		}
		if (key.equals("unknown"))
			isGuess = true;
	}

	public String getRadical() {
		return radical;
	}

	public void setRadical(String radical) {
		this.radical = radical;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public boolean isGuess() {
		return this.isGuess;
	}

}
