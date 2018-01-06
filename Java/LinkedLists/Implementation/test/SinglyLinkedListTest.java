import static org.junit.Assert.*;

import org.junit.Test;

public class SinglyLinkedListTest {

	//NOTE: 
	//Calling multiple methods of the class being tested in a single test case is bad practice
	//However, this is done here since these test cases are for relatively simple code (a data structure)
	
	@Test
	public void testSize_rightAfterInstantiation() {
		SinglyLinkedList list = new SinglyLinkedList();
		assertEquals(0,list.size());
	}
	
	@Test
	public void testEmpty_rightAfterInstantiation() {
		SinglyLinkedList list = new SinglyLinkedList();
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void testEmpty_afterPrependingOneItem() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.pushFront("HelloWorld");
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testPushFront_prependingOneItem() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.pushFront("HelloWorld");
		assertEquals(1,list.size());
	}
	
	@Test
	public void testPushFront_prependingTenItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		for (int i=0; i<10; i++)
			list.pushFront("HelloWorld");
		assertEquals(10,list.size());
	}
	
	@Test
	public void testValueAt_prependingOneItem() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.pushFront("HelloWorld");
		assertEquals("HelloWorld",(String) list.valueAt(0));
	}
	
	@Test
	public void testValueAt_prependingTenIdenticalItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		for (int i=0; i<10; i++)
			list.pushFront("HelloWorld");
		for (int j=0; j<10; j++)
			assertEquals("HelloWorld",(String) list.valueAt(j));
	}

	@Test
	public void testValueAt_prependingTenVaryingItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushFront(letter);
		assertEquals("H",(String) list.valueAt(9));
	}
	
	@Test
	public void testPopFront_afterPrependingTwoItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.pushFront("HelloWorld");
		list.pushFront("DUDE");
		list.popFront();
		assertEquals("HelloWorld",(String) list.valueAt(0));
	}
	
	@Test
	public void testPopFront_afterPrependingTenVaryingItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushFront(letter);
		list.popFront();
		assertEquals("l",(String) list.valueAt(0));
	}
	
	@Test
	public void testPushBack_appendingTenVaryingItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushBack(letter);
		assertEquals("d",(String) list.valueAt(9));
	}
	
	@Test
	public void testPushBack_appendingTwoItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.pushBack("HelloWorld");
		list.pushBack("DUDE");
		assertEquals("HelloWorld",(String) list.valueAt(0));
	}
	
	@Test
	public void testPopBack_afterAppendingTenVaryingItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushBack(letter);
		list.popBack();
		assertEquals("H",(String) list.valueAt(0));
	}
	
	@Test
	public void testFrontVal_afterPrependingTwoItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.pushFront("HelloWorld");
		list.pushFront("DUDE");
		assertEquals("DUDE",(String) list.frontVal());
	}
	
	@Test
	public void testFrontVal_afterAppendingTwoItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.pushBack("HelloWorld");
		list.pushBack("DUDE");
		assertEquals("HelloWorld",(String) list.frontVal());
	}
	
	@Test
	public void testBackVal_afterPrependingTwoItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.pushFront("HelloWorld");
		list.pushFront("DUDE");
		assertEquals("HelloWorld",(String) list.backVal());
	}
	
	@Test
	public void testBackVal_afterAppendingTwoItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.pushBack("HelloWorld");
		list.pushBack("DUDE");
		assertEquals("DUDE",(String) list.backVal());
	}
	
	@Test
	public void testInsert_afterInsertingOneItem() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushBack(letter);
		list.insert("s",5);
		assertEquals("s",(String) list.valueAt(5));
	}
	
	@Test
	public void testInsert_afterInsertingTwoItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushBack(letter);
		list.insert("s",5);
		list.insert("p",5);
		assertEquals("W",(String) list.valueAt(7));
	}
	
	@Test
	public void testErase_afterInsertingItem() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushBack(letter);
		list.insert("s",5);
		list.erase(5);
		assertEquals("W",(String) list.valueAt(5));
	}
	
	@Test
	public void testErase_afterInsertingTwoItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushBack(letter);
		list.insert("s",5);
		list.insert("p",5);
		list.erase(5);
		assertEquals("W",(String) list.valueAt(6));
	}
	
	@Test
	public void testValueNfromEnd_afterInsertingOneItem() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushBack(letter);
		list.insert("s",5);
		assertEquals("s",(String) list.valueNfromEnd(5));
	}
	
	@Test
	public void testValueNfromEnd_afterInsertingTwoItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushBack(letter);
		list.insert("s",5);
		list.insert("p",5);
		assertEquals("W",(String) list.valueNfromEnd(4));
	}
	
	@Test
	public void testRemoveValue_afterAppendingTenVaryingItems() {
		SinglyLinkedList list = new SinglyLinkedList();
		String[] letters = "HelloWorld".split("");
		for (String letter: letters)
			list.pushBack(letter);
		list.removeValue("l");
		assertEquals("W",(String) list.valueAt(4));
	}	
}
