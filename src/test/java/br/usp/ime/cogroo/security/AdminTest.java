package br.usp.ime.cogroo.security;

import static org.junit.Assert.*;

import org.junit.Test;

import br.usp.ime.cogroo.security.Admin;

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
