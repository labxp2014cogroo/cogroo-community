package br.usp.ime.cogroo.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;

@Category(LabXP2014.class)
public class AdminTest {

	private Admin admin = new Admin();

	@Test
	public void getRoleNameTest() {
		assertEquals(admin.getRoleName(), "admin");
	}

	@Test
	public void getCanSetUserRoleTest() {
		assertTrue(admin.getCanSetUserRole());
	}

	@Test
	public void getCanViewSensitiveUserDetailsTest() {
		assertTrue(admin.getCanViewSensitiveUserDetails());
	}

	@Test
	public void getCanEditSensitiveUserDetailsTest() {
		assertTrue(admin.getCanEditSensitiveUserDetails());
	}

	@Test
	public void getCanManageRSSTest() {
		assertTrue(admin.getCanManageRSS());
	}

}
