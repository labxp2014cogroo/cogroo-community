package br.usp.ime.cogroo.dao;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.HSQLDBEntityManagerFactory;
import br.usp.ime.cogroo.model.GrammarCheckerVersion;

public class GrammarCheckerVersionDAOTest {
	
	private EntityManager em;
	private GrammarCheckerVersionDAO versionDAO;

	@Before
	public void setUp() throws Exception {
		em = HSQLDBEntityManagerFactory.createEntityManager();
		versionDAO = new GrammarCheckerVersionDAO(em);
	}
	
	@After
	public void tearDown() {
		em.close();
	}
	
	@Test
	public void testAdd() {
		em.getTransaction().begin();
		GrammarCheckerVersion version = versionDAO.retrieve("1.0.0");
		em.getTransaction().commit();
		assertNotNull(version);
	}
	
	@Test
	public void testAddSame() {
		
		em.getTransaction().begin();
		GrammarCheckerVersion version1 = versionDAO.retrieve("1");
		GrammarCheckerVersion version2 = versionDAO.retrieve("1");
		em.getTransaction().commit();
		assertEquals(version1.getId(), version2.getId());
		
	}
	
	@Test
	public void testDiferentSame() {
		
		em.getTransaction().begin();
		GrammarCheckerVersion version1 = versionDAO.retrieve("1");
		GrammarCheckerVersion version2 = versionDAO.retrieve("2");
		em.getTransaction().commit();
		assertNotSame(version1.getId(), version2.getId());
		
	}

}
