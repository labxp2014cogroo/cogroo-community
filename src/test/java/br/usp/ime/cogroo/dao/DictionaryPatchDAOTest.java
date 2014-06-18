package br.usp.ime.cogroo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.HSQLDBEntityManagerFactory;
import br.usp.ime.cogroo.model.DictionaryPatch;
import br.usp.ime.cogroo.model.User;
import br.usp.ime.cogroo.model.errorreport.Comment;
import br.usp.ime.cogroo.model.errorreport.State;

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
	public void bdShoulHaveOnePatch() {

		List<DictionaryPatch> actual = dictionaryPatchDAO.listAll();
		Assert.assertEquals(1, actual.size());

	}

	private void populateWithOnePatch() {
		dictionaryPatchDAO = new DictionaryPatchDAO(em);

		Date creation = new Date(0);
		Date modified = new Date(1000);
		State state = State.OPEN;

		Comment comment = new Comment();
		comment.setComment("a comment");

		List<Comment> comments = new ArrayList<Comment>();
		comments.add(comment);

		User user = new User("rodrigo");
		populateUser(user);
		String newEntry = "Nova Entrada";
		String previousEntry = "Entrada Velha";

		patch = new DictionaryPatch(comments, user, creation, modified, state,
				newEntry, previousEntry);
		em.getTransaction().begin();
		dictionaryPatchDAO.add(patch);
		em.getTransaction().commit();
	}

	private void populateUser(User user) {
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
