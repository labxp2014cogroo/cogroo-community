package br.usp.ime.cogroo.model;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;


public class UserTest {
	
	@Test
	public void shouldGetNameOfUser() {
		User william = new User("william");
		assertEquals("william", william.getLogin());
	
		User colen = new User("Colen");
		assertEquals("Colen", colen.getLogin());
			
	}
	
}
