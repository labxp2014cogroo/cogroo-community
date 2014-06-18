package br.usp.ime.cogroo.integration.stories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SearchWordStory {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome",
				"http://localhost:8080/");
		selenium.start();
	}

	@Test
	public void testSearchWordStory() throws Exception {
		selenium.open("/");
		selenium.click("link=Consultar palavra");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=text", "casa");
		selenium.click("id=go");
		selenium.waitForPageToLoad("30000");
		assertEquals("feminino, singular",
				selenium.getText("//table[@id='table_morf']/tbody/tr/td[4]"));
		assertEquals("verbo",
				selenium.getText("//table[@id='table_morf']/tbody/tr[2]/td[3]"));
		assertEquals("presente, terceira, singular, transitivo",
				selenium.getText("//table[@id='table_morf']/tbody/tr[2]/td[4]"));
		assertEquals("verbo",
				selenium.getText("//table[@id='table_morf']/tbody/tr[3]/td[3]"));
		assertEquals("imperativo, segunda, singular, transitivo",
				selenium.getText("//table[@id='table_morf']/tbody/tr[3]/td[4]"));
		assertEquals("substantivo comum",
				selenium.getText("//table[@id='table_morf']/tbody/tr/td[3]"));
	}

	@Test
	public void testSearchNonExistantWordStory() throws Exception {
		selenium.open("/");
		selenium.click("link=Consultar palavra");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=text", "palavraInexistente");
		selenium.click("id=go");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getText("css=div.yellow_box").contains(
				"palavraInexistente"));
		assertEquals("Sugerir", selenium.getValue("css=input.a_button"));
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
