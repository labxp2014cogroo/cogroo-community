package br.usp.ime.cogroo.model;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ParserYaml {

	private static ParserYaml singleton = null;
	public final static String YAML_FILE = "/port.yaml";

	private ParserYaml() throws FileNotFoundException, UnsupportedEncodingException {
		Map<String, Pair<String, HashMap<String, String>>> hash = new HashMap<String, Pair<String, HashMap<String, String>>>();
		this.parse(hash);
		this.hash = Collections.unmodifiableMap(hash);
	}

	public static ParserYaml getInstance() throws FileNotFoundException, UnsupportedEncodingException{
		if (ParserYaml.singleton == null){
			ParserYaml.singleton = new ParserYaml();
		}
		return ParserYaml.singleton;
	}

	private final Map<String, Pair<String, HashMap<String, String>>> hash;

	private static void ignoreHeader (Scanner scan){
		while (scan.hasNext() && !scan.nextLine().equals(" PROPS:"));
	}

	private void generateFirstLevel (Scanner scan, Map<String, Pair<String, HashMap<String, String>>> hash){
		String line;
		String[] pair;
		while (scan.hasNext()){
			line = scan.nextLine().trim();
			if (line.matches("[a-zA-Z0-9]+: [a-zA-Z]+.*")){
				pair = line.split(":");
				hash.put(pair[0].trim(), new Pair<String, HashMap<String,String>>(pair[1].trim(), null));
			}else {
				break;
			}
		}
	}

	private void generateSecondLevel(Scanner scan, Map<String, Pair<String, HashMap<String, String>>> hash){
		String line;
		String[] pair;
		String key;
		HashMap<String, String> secondLevel = new HashMap<String, String>();
		while (scan.hasNext()){
			line = scan.nextLine().trim();
			if (line.matches("[a-zA-Z]+:$")){
				key = line.split(":")[0].trim();
				secondLevel = new HashMap<String, String>();
				hash.get(key).setB(secondLevel);
			}else if (line.matches("[_a-zA-Z0-9]+:\\s*-?[_0-9a-zA-Z]+.*")){
				pair = line.split(":");
				secondLevel.put(pair[0].trim(), pair[1].trim());
			}
		}
	}

	private void parse (Map<String, Pair<String, HashMap<String, String>>> hash) throws FileNotFoundException, UnsupportedEncodingException {
		InputStream portYamlInputStream = this.getClass().getResourceAsStream(ParserYaml.YAML_FILE);
		Reader reader = new InputStreamReader(portYamlInputStream, "UTF-8");
		Scanner scan = new Scanner(reader);
		ParserYaml.ignoreHeader(scan);
		this.generateFirstLevel(scan, hash);
		this.generateSecondLevel(scan, hash);
	}

	/**
	 * Returns the value associated with the key or null, if the key does not exit
	 * @param key
	 * @return CAT --> categoria
	 *             G --> Gênero
	 */
	public  String getValue(String key){
		if (this.hash.containsKey(key)){
			return this.hash.get(key).getA();
		}
		return null;
	}

	public String getValue(String category, String property) {
		if (this.hash.containsKey(category)){
			HashMap<String, String> hashProperty = this.hash.get(category).getB(); 
			if (hashProperty.containsKey(property)) {
				return hashProperty.get(property); 
			}
		}
		return null;
	}

}
