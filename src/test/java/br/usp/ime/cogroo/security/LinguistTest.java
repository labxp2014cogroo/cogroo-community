package br.usp.ime.cogroo.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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

	@Test
	public void getCanApproveDictionaryEditionsTest() {
		assertTrue(linguist.getCanApproveDictionaryEditions());
	}

}
