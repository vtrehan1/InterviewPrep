import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayQueueTest {

	@Test
	public void testSize_rightAfterInstantiation() {
		ArrayQueue queue = new ArrayQueue(10);
		assertEquals(0,queue.size());
	}

	@Test
	public void testIsEmpty_rightAfterInstantiation() {
		ArrayQueue queue = new ArrayQueue(10);
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testIsEmpty_afterEnqueuingOneItem() {
		ArrayQueue queue = new ArrayQueue(10);
		queue.enqueue("HelloWorld");
		assertFalse(queue.isEmpty());
	}
	
	@Test
	public void testEnqueue_enqueuingOneItem() {
		ArrayQueue queue = new ArrayQueue(10);
		queue.enqueue("HelloWorld");
		assertEquals("HelloWorld",(String) queue.dequeue());
	}
	
	@Test
	public void testDequeue_enqueuingTenItems() {
		ArrayQueue queue = new ArrayQueue(10);
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			queue.enqueue(letter);
		for (int i=0; i<8; i++)
			queue.dequeue();
		assertEquals("l",(String) queue.dequeue());
	}
}