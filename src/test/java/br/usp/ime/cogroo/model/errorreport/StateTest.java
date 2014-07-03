package br.usp.ime.cogroo.model.errorreport;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

	@Test(expected = IllegalArgumentException.class)
	public void stateFromStringExceptionTest() {
		State state = State.fromValue("ThisIsNotAState");
	}
}
