package br.usp.ime.cogroo.model.errorreport;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import utils.LabXP2014;

import com.mchange.util.AssertException;

@Category(LabXP2014.class)
public class StateTest {

	@Test
	public void stateTest() {
		assertEquals(State.OPEN.value(), "open"); 
		assertEquals(State.RESOLVED.value(), "resolved"); 
		assertEquals(State.REJECTED.value(), "reject"); 
	}
	
	@Test
	public void stateFromStringOKTest() {
		State state = State.fromValue("open"); 
		assertEquals(state, State.OPEN);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void stateFromStringExceptionTest() {
		State state = State.fromValue("ThisIsNotAState"); 
	}
}
