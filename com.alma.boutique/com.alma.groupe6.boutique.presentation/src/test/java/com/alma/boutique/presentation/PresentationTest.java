package com.alma.boutique.presentation;


import org.junit.Test;

/**
 * Created by thomas on 25/10/16.
 */
public class PresentationTest {

	@Test
	public void testInit() {
		PresentationServlet p = new PresentationServlet();
		p.init();
		assert(true);
	}
}