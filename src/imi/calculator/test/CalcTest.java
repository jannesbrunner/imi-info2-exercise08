/**
 * 
 */
package imi.calculator.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;

import org.junit.Ignore;
import org.junit.Test;
import org.hamcrest.core.*;

import imi.calculator.CalcEngine;
import imi.calculator.HexUserInterface;
import imi.calculator.Postfix;
import imi.calculator.Stack;


/**
 * @author JayBee
 *
 */
public class CalcTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	private CalcEngine engine;
	private Stack<Integer> stack;

   
	
	@Before
	public void setUp() throws Exception {
		
		engine = new CalcEngine();
		stack = new Stack<Integer>();

        
				
	}
	
	// Calc Engine Test
	@Test
	public void simpleArithmetic() {

				
		
		String test;
		
		// Addition
		test = engine.getDecSolution("5 + 5");
		assertTrue("5 + 5 should be 10", test.equals("10"));
		test = engine.getDecSolution("10 + 10");
		assertTrue("10 + 10 should be 20", test.equals("20"));
		test = engine.getDecSolution("7 + 3");
		assertTrue("7 + 3 should be 10", test.equals("10"));
		test = engine.getDecSolution("100 + 333");
		assertTrue("100 + 333 should be 10", test.equals("433"));
		
		// Subtraction
		test = engine.getDecSolution("5 - 5");
		assertTrue("5 - 5 should be 0", test.equals("0"));
		test = engine.getDecSolution("10 - 10");
		assertTrue("10 - 10 should be 0", test.equals("0"));
		test = engine.getDecSolution("7 - 3");
		assertTrue("7 - 3 should be 10", test.equals("4"));
		test = engine.getDecSolution("100 - 300");
		assertTrue("100 - 300 should be 10", test.equals("-200"));

		// Division
		test = engine.getDecSolution("5 / 5");
		assertTrue("5 / 5 should be 1", test.equals("1"));
		test = engine.getDecSolution("15 / 10");
		assertTrue("15 / 10 should be 2 (rounded)", test.equals("2"));
		test = engine.getDecSolution("7 / 3");
		assertTrue("7 / 3 should be 2 (rounded)", test.equals("2"));
		test = engine.getDecSolution("50 / 100");
		assertTrue("50 / 100 should be 1 (rounded)", test.equals("1"));
		
		// Multiplication
		test = engine.getDecSolution("5 * 5");
		assertTrue("5 * 5 should be 25", test.equals("25"));
		test = engine.getDecSolution("15 * 10");
		assertTrue("15 * 10 should be 1.5", test.equals("150"));
		test = engine.getDecSolution("7 * 3");
		assertTrue("7 * 3 should be 21", test.equals("21"));
		test = engine.getDecSolution("50 * 100");
		assertTrue("50 * 100 should be ", test.equals("5000"));
		
	}
	
	@Test
	public void math_precedenceTest() {
		String test;
		int result;
		
		test = engine.getDecSolution("5 + 3 * 4");
		result = Integer.parseInt(test);
		assertEquals("5 + 3 * 4 should be 17", 17, result);
		
		test = engine.getDecSolution("2 + 3 * 4 / 5");
		result = Integer.parseInt(test);
		assertEquals("2 + 3 * 4 / 5 should be 5 (rounded)", 5, result);
		
		
		test = engine.getDecSolution("5 * 5 / 5");
		result = Integer.parseInt(test);
		assertEquals("5 * 5 / 5 should be 5", 5, result);
		
		test = engine.getDecSolution("5 / 3 + 2 - 5 * 2");
		result = Integer.parseInt(test);
		assertEquals("5 / 3 + 2 - 5 * 2 should be -6 ", -6, result);
		
				
	}
	
	@Test
	public void hex_arithmetic() {
		String test;
		int result;
		
		test = engine.getHexSolution("f + f");
		result = Integer.parseInt(test, 16);
		
		assertEquals("Hex Solution should be 1e", "1e", test);
		assertEquals("F + F should be 1E(30 Dec)", 30, result);
		
		test = engine.getHexSolution("e - f");
		result = Integer.parseInt(test, 16);
		
		assertEquals("Hex Solution should be FFF(-1 Dec)", "-1", test);
		assertEquals("E - F should be fff(-1 Dec)", -1, result);
		
		test = engine.getHexSolution("f * f");
		result = Integer.parseInt(test, 16);
		
		assertEquals("Hex Solution should be e1(225 Dec)", "e1", test);
		assertEquals("F * F should be e1(225 Dec)",225 , result);
		
		test = engine.getHexSolution("f / f");
		result = Integer.parseInt(test, 16);
		
		assertEquals("Hex Solution should be 1(1 Dec)", "1", test);
		assertEquals("F / F should be 1(1 Dec)", 1, result);
		
		
	}
	
	@Test
	public void Hex2DecAndDec2Hex() {
		String hex = "1e + a - 5f";
		String dec = "30 + 10 - 95";
		
		assertEquals("Convert from Hex to Dec: ", dec, engine.getDecDisplayValue(hex));
		assertEquals("Convert from Dec to Hex: ", hex, engine.getHexDisplayValue(dec));
		
		assertEquals("Convert within: ", dec, engine.getDecDisplayValue(engine.getHexDisplayValue(dec)));
	}
	
	
	// Stack Tests
	
	
	@Test
	public void tryEmptyPopReturnsNull() {
		
		assertNull("Try to pop an empyt Stack", stack.pop());
		
	}
	
	
	@Ignore("Fucks up your machine") @Test(expected=StackOverflowError.class)
	public void tryEmptyPushThrowsException() {
		while(true) {
			stack.push(1);
		}
	}
	
	@Test
	public void filedStackisNotEmpty() {
		stack.push(1);
		
		assertFalse("Stack contains one element", stack.isEmpty());
		
		stack.pop();
	}
	
	
	// Set Calculation Tests
	
	@Test
	public void UnionTwoSets() {
		
		String setsToUnion = "{ 5, 3, 7 } + { 5, 3, 8, 9, }";
		String result = engine.unionSets(setsToUnion);
		assertEquals("{ 5, 3, 7 } + { 5, 3, 8, 9, } = { 5, 3, 7, 8, 9 }", "{ 3, 5, 7, 8, 9 }", result);
			
		
	}
	
	@Test
	public void SubstractTwoSets() {
		
		String setsToSubstract = "{ 5, 3, 7 } - { 5, 3, 8, 9, }";
		String result = engine.subtractSets(setsToSubstract);
		
		assertEquals("{ 5, 3, 7 } - { 5, 3, 8, 9, } = { 7 }", "{ 7 }", result);
			
		
	}
	
	@Test
	public void IntersectionTwoSets() {
		
		String setsToSubstract = "{ 5, 3, 7 } ∩ { 5, 3, 8, 9, }";
		String result = engine.intersectionSets(setsToSubstract);
		
		assertEquals("{ 5, 3, 7 } ∩ { 5, 3, 8, 9, } = { 3, 5 }", "{ 3, 5 }", result);
			
		
	}
	

}
