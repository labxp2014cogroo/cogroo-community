package br.usp.ime.cogroo.security;

import org.junit.Test;

import static org.junit.Assert.*; 

public class LinguistTest {
	
	Linguist linguist = new Linguist(); 
	
	@Test
	public void getRoleNameTest() {
		assertEquals(linguist.getRoleName(), "linguist");
	}
	
	@Test
	public void getCanSetErrorReportPriorityTest() {
		assertTrue(linguist.getCanSetErrorReportPriority());
	}
	
	@Test
	public void getCanSetErrorReportStateTest() {
		assertTrue(linguist.getCanSetErrorReportState());
	}
	
	@Test
	public void getCanEditErrorReportTest() {
		assertTrue(linguist.getCanEditErrorReport());
	}

}
