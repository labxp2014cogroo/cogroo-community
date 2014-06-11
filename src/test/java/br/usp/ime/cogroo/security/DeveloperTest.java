package br.usp.ime.cogroo.security;

import org.junit.Test;
import static org.junit.Assert.*;

public class DeveloperTest {
	
	Developer developer = new Developer(); 

	@Test
	public void getRoleNameTest() {
		assertEquals(developer.getRoleName(), "developer");
	}
	
	@Test
	public void getCanDeleteOtherUserCommmentTest() {
		assertTrue(developer.getCanDeleteOtherUserCommment());
	}
	
	@Test
	public void getCanDeleteOtherUserErrorReportTest() {
		assertTrue(developer.getCanDeleteOtherUserErrorReport());
	}
	
	@Test
	public void getCanRefreshStatusTest() {
		assertTrue(developer.getCanRefreshStatus());
	}
	
}
