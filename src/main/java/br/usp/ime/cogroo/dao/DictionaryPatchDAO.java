package br.usp.ime.cogroo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.caelum.vraptor.ioc.Component;
import br.usp.ime.cogroo.model.DictionaryPatch;
import br.usp.ime.cogroo.model.User;

@Component
public class DictionaryPatchDAO {
	EntityManager em;

	public static final String PATCH_ENTITY = DictionaryPatch.class.getName();

	public DictionaryPatchDAO(EntityManager e) {
		em = e;
	}

	public DictionaryPatch retrieve(Long id) {
		DictionaryPatch patch = em.find(DictionaryPatch.class, id);
		return patch;
	}

	@SuppressWarnings("unchecked")
	public List<DictionaryPatch> retrieveAll() {
		return em.createQuery("from " + PATCH_ENTITY).getResultList();
	}

	public void add(DictionaryPatch patch) {
		try {
			em.persist(patch);
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	public void addInsertionPatch(String entry, User user) {
		DictionaryPatch dictionaryPatch = new DictionaryPatch();
		dictionaryPatch.setNewEntry(entry);
		dictionaryPatch.setUser(user);
		add(dictionaryPatch);
	}

	public void addEditionPatch(String oldEntry, String newEntry, User user) {
		DictionaryPatch dictionarypatch = new DictionaryPatch();
		dictionarypatch.setNewEntry(newEntry);
		dictionarypatch.setPreviousEntry(oldEntry);
		dictionarypatch.setUser(user);
		add(dictionarypatch);
	}

	@SuppressWarnings("unchecked")
	public List<DictionaryPatch> listAll() {
		return em.createQuery("from " + PATCH_ENTITY).getResultList();
	}

}
