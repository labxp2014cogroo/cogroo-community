package br.usp.ime.cogroo.model;

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class ParserYaml {
	
	private static ParserYaml singleton = null;
	
	private ParserYaml() throws FileNotFoundException {
		this.parse("port.yaml");
	}
	
	public static ParserYaml getInstance() throws FileNotFoundException{
		if (ParserYaml.singleton == null){
			ParserYaml.singleton = new ParserYaml();
		}
		return ParserYaml.singleton;
	}
	
	private  final HashMap<String, Pair<String, HashMap<String, String>>> hash = new HashMap<String, Pair<String, HashMap<String, String>>>();
	
	/* XXX: static or not ? */
	private static void ignoreHeader (Scanner scan){
		while (scan.hasNext() && !scan.nextLine().equals(" PROPS:"));
	}
	
	private void generateFirstLevel (Scanner scan){
		String line;
		String[] pair;
		while (scan.hasNext()){
			line = scan.nextLine().trim();
			if (line.matches("[a-zA-Z0-9]+: [a-zA-Z]+.*")){
				pair = line.split(":");
				this.hash.put(pair[0].trim(), new Pair<String, HashMap<String,String>>(pair[1].trim(), null));
			}else {
				break;
			}
		}
	}
	
	private void generateSecondLevel(Scanner scan){
		String line;
		String[] pair;
		String key;
		HashMap<String, String> secondLevel = new HashMap<String, String>();
		while (scan.hasNext()){
			line = scan.nextLine().trim();
			if (line.matches("[a-zA-Z]+:$")){
				key = line.split(":")[0].trim();
				secondLevel = new HashMap<String, String>();
				this.hash.get(key).setB(secondLevel);
			}else if (line.matches("[_a-zA-Z0-9]+:\\s*-?[_0-9a-zA-Z]+.*")){
				pair = line.split(":");
				secondLevel.put(pair[0].trim(), pair[1].trim());
			}
		}
	}
	
	private  void parse (String fileName) throws FileNotFoundException{		
		Scanner scan = new Scanner(new File (fileName));
		ParserYaml.ignoreHeader(scan);
		this.generateFirstLevel(scan);
		this.generateSecondLevel(scan);
	}
	
	/**
	 * Returns the value associated with the key or null, if the key does not exit
	 * @param key
	 * @return CAT --> categoria
	 * 			G --> Gênero
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
