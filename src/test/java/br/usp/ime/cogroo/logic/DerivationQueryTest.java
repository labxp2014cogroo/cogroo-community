package br.usp.ime.cogroo.logic;

// d=abismalidades, F=hiperabismal G=abismalices, A=antiabismal, L=abismal-lhas, O=pós-abismal

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

public class DerivationQueryTest {
	
	@Test
	public void testqueryDerivations() {
		
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
}
