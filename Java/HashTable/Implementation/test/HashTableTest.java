import static org.junit.Assert.*;

import org.junit.Test;

public class HashTableTest {

	//NOTE: 
	//Calling multiple methods of the class being tested in a single test case is bad practice
	//However, this is done here since these test cases are for relatively simple code (a data structure)
	
	@Test
	public void testAdd_afterCapacityFull() {
		HashTable hashTable = new HashTable(100);
		for (int i=0; i<100; i++)
			hashTable.add(i,i);
		try {
			hashTable.add(100,100);
			fail("RuntimeException was not thrown");
		} 
		catch (RuntimeException e) {}
	}
	
	@Test
	public void testAdd_withIntentionalCollision() {
		HashTable hashTable = new HashTable(100);
		hashTable.add(0,"John");
		hashTable.add(100, "Sarah");
		assertEquals("John", (String) hashTable.get(0));
	}
	
	@Test
	public void testGet_withValidKey() {
		HashTable hashTable = new HashTable(100);
		hashTable.add(0,"John");
		assertEquals("John", (String) hashTable.get(0));
	}
	
	@Test
	public void testGet_withInvalidKey() {
		HashTable hashTable = new HashTable(100);
		hashTable.add(0,"John");
		assertEquals(null,hashTable.get(5));
	}
	
	@Test
	public void testRemove_withValidKeyAndCollision() {
		HashTable hashTable = new HashTable(100);
		hashTable.add(0,"John");
		hashTable.add(100, "Sarah");
		hashTable.remove(1);
		assertEquals(null,hashTable.get(1));
	}
	
	@Test
	public void testRemove_withInvalidKey() {
		HashTable hashTable = new HashTable(100);
		hashTable.add(0,"John");
		hashTable.add(100, "Sarah");
		hashTable.remove(2);
		assertEquals(null,hashTable.get(2));
	}
}