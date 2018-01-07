import static org.junit.Assert.*;

import org.junit.Test;

public class StackTest {

	//NOTE: 
	//Calling multiple methods of the class being tested in a single test case is bad practice
	//However, this is done here since these test cases are for relatively simple code (a data structure)
	
	@Test
	public void testSize_afterInstantiation() {
		Stack stack = new Stack();
		assertEquals(0, stack.size());
	}
	
	@Test
	public void testSize_addingTenElements() {
		Stack stack = new Stack();
		for (int i=0; i<9; i++)
			stack.push("HelloWorld");
		stack.push("Last add");
		assertEquals(10, stack.size());
	}
	
	@Test
	public void testSize_addingTenElementsAndRemovingOne() {
		Stack stack = new Stack();
		for (int i=0; i<9; i++)
			stack.push("HelloWorld");
		stack.push("Last add");
		stack.pop();
		assertEquals(9, stack.size());
	}
	
	@Test
	public void testIsEmpty_afterInstantiation() {
		Stack stack = new Stack();
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void testPush_addingOneElement() {
		Stack stack = new Stack();
		stack.push("HelloWorld");
		assertEquals("HelloWorld", (String) stack.top());
	}
	
	@Test
	public void testPush_addingTenElements() {
		Stack stack = new Stack();
		for (int i=0; i<9; i++)
			stack.push("HelloWorld");
		stack.push("Last add");
		assertEquals("Last add", (String) stack.top());
	}
	
	@Test
	public void testPop_addingTenElementsAndRemovingOne() {
		Stack stack = new Stack();
		for (int i=0; i<9; i++)
			stack.push("HelloWorld");
		stack.push("Last add");
		assertEquals("Last add", (String) stack.pop());
	}
}