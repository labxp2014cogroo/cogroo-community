package br.usp.ime.cogroo.logic;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;

@Category(LabXP2014.class)
public class DerivationQueryTest {
	
	@Test
	public void testQueryDerivations() {
		
		HashMap<String, String> derivationsHash = DerivationsQuery.queryDerivations("abismal/#an/");
		assertEquals(derivationsHash.get("d"), "abismalidades");
		assertEquals(derivationsHash.get("F"), "hiperabismal");
		assertEquals(derivationsHash.get("G"), "abismalices");
		assertEquals(derivationsHash.get("A"), "antiabismal");
		assertEquals(derivationsHash.get("L"), "abismal-lhas");
		assertEquals(derivationsHash.get("O"), "pós-abismal");
		
		derivationsHash = DerivationsQuery.queryDerivations("correr/#vn/");
		
		assertEquals(derivationsHash.get("c"), "correção");
		assertEquals(derivationsHash.get("B"), "corridamente");
		assertEquals(derivationsHash.get("L"), "correr-se");
		assertEquals(derivationsHash.get("M"), "corrimentos");
		assertEquals(derivationsHash.get("N"), "corridíssimo");
		assertEquals(derivationsHash.get("V"), "vice-correr");
		
	}
	
	@Test 
	public void testRelevantDerivations () {
		HashMap<String, String> relevantDerivations = DerivationsQuery.queryDerivations("abismal"); 
		
		for (String key : relevantDerivations.keySet()) {
			assertEquals(key.length(), 1);
		}
	}
	
	@Test
	public void testGetDerivationsFromFlags() {
		
		HashMap<String, Set<String>> derivationsHash = DerivationsQuery.getDerivationsFromFlags("abismal/#an/p");
		Set<String> test = new HashSet<String>();
		test.add("abismais");
		assertEquals(derivationsHash.get("p"), test);
		
		derivationsHash = DerivationsQuery.getDerivationsFromFlags("amar/#v/X");
		test = new HashSet<String>();
		test.add("amai");
		test.add("ames");
		test.add("amas");
		test.add("amemos");
		test.add("amem");
		test.add("ame");
		test.add("ameis");
		test.add("amam");
		test.add("amo");
		test.add("amais");
		test.add("ama");
		test.add("amamos");
		
		assertEquals(derivationsHash.get("X"), test);
		
		derivationsHash = DerivationsQuery.getDerivationsFromFlags("amar/#v/Xn");
		test = new HashSet<String>();
		test.add("amai");
		test.add("ames");
		test.add("amas");
		test.add("amemos");
		test.add("amem");
		test.add("ame");
		test.add("ameis");
		test.add("amam");
		test.add("amo");
		test.add("amais");
		test.add("ama");
		test.add("amamos");
		
		assertEquals(derivationsHash.get("X"), test);
		
		test = new HashSet<String>();
		test.add("amante");
		test.add("amantes");
		
		assertEquals(derivationsHash.get("n"), test);
	}
		
}
