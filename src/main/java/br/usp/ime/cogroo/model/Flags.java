package br.usp.ime.cogroo.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Flags {

	private static Flags singleton = null;
	private Properties flagsProperties = null;
	
	
	private Flags() throws IOException {
		this.flagsProperties = new Properties();
		this.flagsProperties.load(new FileInputStream(new File("flags.properties")));
	}
	

	public static Flags getInstance() throws IOException{
		if (Flags.singleton == null){
			Flags.singleton = new Flags();
		}
		return Flags.singleton;
	}
	
	public String getTextFromFlag(String flag){
		return this.flagsProperties.getProperty(flag).trim();
	}
	
	
}
