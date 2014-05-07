package br.usp.ime.cogroo.integration.stories.common;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.thoughtworks.selenium.Selenium;

public class ThenAsserts {
	private final Selenium browser;
	
	public ThenAsserts(Selenium browser) {
		this.browser = browser;
	}
	
	public void iGetAnErrorWithDescription(String description) {
			System.out.println(description);
		assertTrue(browser.isTextPresent(description));
	}

	public void iMustBeLoggedInAs(String name) {
		assertTrue(browser.isTextPresent(name));	
		assertTrue(browser.isTextPresent("logout"));	
	}

	public void iMustNotBeLoggedIn() {
		assertFalse(browser.isTextPresent("logout"));	
	}

	public void theWordAppearsOnList(String string) {
		// TODO Auto-generated method stub
		
	}
}
