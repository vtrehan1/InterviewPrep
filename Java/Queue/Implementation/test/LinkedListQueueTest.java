import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListQueueTest {

	//NOTE: 
	//Calling multiple methods of the class being tested in a single test case is bad practice
	//However, this is done here since these test cases are for relatively simple code (a data structure)
	
	@Test
	public void testSize_rightAfterInstantiation() {
		LinkedListQueue queue = new LinkedListQueue();
		assertEquals(0,queue.size());
	}

	@Test
	public void testIsEmpty_rightAfterInstantiation() {
		LinkedListQueue queue = new LinkedListQueue();
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testIsEmpty_afterEnqueuingOneItem() {
		LinkedListQueue queue = new LinkedListQueue();
		queue.enqueue("HelloWorld");
		assertFalse(queue.isEmpty());
	}
	
	@Test
	public void testEnqueue_enqueuingOneItem() {
		LinkedListQueue queue = new LinkedListQueue();
		queue.enqueue("HelloWorld");
		assertEquals("HelloWorld",(String) queue.dequeue());
	}
	
	@Test
	public void testEnqueue_enqueuingTenItems() {
		LinkedListQueue queue = new LinkedListQueue();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			queue.enqueue(letter);
		for (int i=0; i<8; i++)
			queue.dequeue();
		assertEquals("l",(String) queue.dequeue());
	}
}