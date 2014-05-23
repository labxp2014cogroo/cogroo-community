package br.usp.ime.cogroo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.caelum.vraptor.ioc.Component;
import br.usp.ime.cogroo.model.DictionaryPatch;

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
	
	public void add(DictionaryPatch patch) {
		try {
			em.persist(patch);
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DictionaryPatch> listAll() {
		return em.createQuery("from " + PATCH_ENTITY).getResultList();
	}
}



