package br.usp.ime.cogroo.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;

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
