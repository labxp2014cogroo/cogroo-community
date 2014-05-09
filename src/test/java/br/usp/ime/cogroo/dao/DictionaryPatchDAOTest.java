package br.usp.ime.cogroo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.HSQLDBEntityManagerFactory;
import br.usp.ime.cogroo.model.DictionaryPatch;
import br.usp.ime.cogroo.model.User;

public class DictionaryPatchDAOTest {

	private EntityManager em;
	private DictionaryPatchDAO dictionaryPatchDAO;
	private DictionaryPatch patch;
	private UserDAO userDAO; 

	@Before
	public void setUp() {
		em = HSQLDBEntityManagerFactory.createEntityManager();
		populateWithOnePatch();
	}
	
	@Test
	public void bdShoulHaveOnePatch () {
		
		List<DictionaryPatch> actual = dictionaryPatchDAO.listAll(); 
		Assert.assertEquals(1, actual.size());
		
	}

	private void populateWithOnePatch() {
		dictionaryPatchDAO = new DictionaryPatchDAO(em);

		String comment = "Comentario"; 
		Short approved = 1; 
		User user = new User("rodrigo");  
		populateUser(user);
		String newEntry = "Nova Entrada"; 
		String previousEntry = "Entrada Velha";
		
		patch = new DictionaryPatch(comment, approved, user, newEntry, previousEntry); 
		em.getTransaction().begin();
		dictionaryPatchDAO.add(patch);
		em.getTransaction().commit();
		
	}
	
	private void populateUser (User user) {
		userDAO = new UserDAO(em); 
		em.getTransaction().begin();
		userDAO.add(user); 
		em.getTransaction().commit();
		
	}

	@After
	public void tearDown() {
		em.close();
	}
	

}
