package br.usp.ime.cogroo.integration.stories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class PatchListStory {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/");
		selenium.start();
	}

	@Test
	public void testPatchListStory() throws Exception {
		selenium.open("/");
		selenium.click("css=#loginlink > p > a > b");
		selenium.type("name=login", "admin");
		selenium.type("name=password", "admin");
		selenium.click("name=entrar");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Palavras sugeridas");
		selenium.waitForPageToLoad("30000");
		assertEquals("Comentários", selenium.getText("//table[@id='entriesList']/thead/tr/th[8]"));
		assertTrue(selenium.isElementPresent("name=entriesList_length"));
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
