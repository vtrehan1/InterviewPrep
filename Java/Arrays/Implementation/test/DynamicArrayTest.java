import static org.junit.Assert.*;

import org.junit.Test;

public class DynamicArrayTest {

	//NOTE: 
	//Calling multiple methods of the class being tested in a single test case is bad practice
	//However, this is done here since these test cases are for relatively simple code (a data structure) 
	
	@Test
	public void testSize_rightAfterInitialization() {
		DynamicArray array = new DynamicArray();
		assertEquals(0,array.size());
	}
	
	@Test
	public void testSize_afterThreeElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<3; i++)
			array.push("HelloWorld");
		assertEquals(3,array.size());
	}
	
	@Test
	public void testInsert_afterThreeElementsAddedAndInsertion() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<3; i++)
			array.push("HelloWorld");
		array.insert(0, "HelloJohn");
		assertEquals(4,array.size());
	}
	
	@Test
	public void testCapacity_rightAfterInitialization() {
		DynamicArray array = new DynamicArray();
		assertEquals(16,array.capacity());
	}
	
	@Test
	public void testCapacity_afterInitializationWithSize() {
		DynamicArray array = new DynamicArray(10);
		assertEquals(10,array.capacity());
	}
	
	@Test
	public void testIsEmpty_rightAfterInitialization() {
		DynamicArray array = new DynamicArray();
		assertTrue(array.isEmpty());
	}
	
	@Test
	public void testIsEmpty_afterOneItemPushed() {
		DynamicArray array = new DynamicArray();
		array.push("HelloWorld");
		assertFalse(array.isEmpty());
	}
	
	@Test
	public void testGet_afterZeroElementsAdded() {
		DynamicArray array = new DynamicArray();
		try {
			String str = (String) array.get(0);
			fail("ArrayIndexOutOfBoundsException wasn't thrown");
		}
		catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	@Test
	public void testGet_afterThreeElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<3; i++)
			array.push("HelloWorld");
		assertEquals("HelloWorld",(String) array.get(1));
	}
	
	@Test
	public void testUpdate_afterZeroElementsAdded() {
		DynamicArray array = new DynamicArray();
		try {
			array.update(0, "HelloWorld");
			fail("ArrayIndexOutOfBoundsException wasn't thrown");
		}
		catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	@Test
	public void testUpdate_afterThreeElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<3; i++)
			array.push("HelloWorld");
		array.update(1,"HelloJohn");
		assertEquals("HelloJohn",(String) array.get(1));
	}
	
	@Test
	public void testInsert_afterZeroElementsAdded() {
		DynamicArray array = new DynamicArray();
		array.insert(0,"HelloJohn");
		assertEquals("HelloJohn",(String) array.get(0));
	}
	
	@Test
	public void testInsert_afterThreeElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<3; i++)
			array.push("HelloWorld");
		array.insert(0, "HelloJohn");
		assertEquals("HelloWorld",(String) array.get(3));
	}
	
	@Test
	public void testPrepend_afterZeroElementsAdded() {
		DynamicArray array = new DynamicArray();
		array.prepend("HelloJohn");
		assertEquals("HelloJohn",(String) array.get(0));
	}
	
	@Test
	public void testPrepend_afterTenElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<10; i++)
			array.push("HelloWorld");
		array.prepend("HelloJohn");
		assertEquals("HelloWorld",(String) array.get(3));
	}
	
	@Test
	public void testDelete_afterTenElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<10; i++)
			array.push("HelloWorld");
		array.delete(0);
		assertEquals("HelloWorld",(String) array.get(8));
	}
	
	@Test
	public void testDelete_afterZeroElementsAdded() {
		DynamicArray array = new DynamicArray();
		try {
			array.delete(0);
			fail("ArrayIndexOutOfBoundsException wasn't thrown");
		}
		catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	@Test
	public void testPop_afterZeroElementsAdded() {
		DynamicArray array = new DynamicArray();
		try {
			array.pop();
			fail("ArrayIndexOutOfBoundsException wasn't thrown");
		}
		catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	@Test
	public void testPop_afterTenElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<10; i++)
			array.push("HelloWorld");
		array.pop();
		assertEquals(9,array.size());
	}
	
	@Test
	public void testRemove_afterTenIdenticalElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<10; i++)
			array.push("HelloWorld");
		array.remove("HelloWorld");
		assertEquals(0,array.size());
	}
	
	@Test
	public void testFind_afterTenIdenticalElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<10; i++)
			array.push("HelloWorld");
		assertEquals(0,array.find("HelloWorld"));
	}
	
	@Test
	public void testFind_afterTenElementsAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<10; i++)
			array.push("HelloWorld");
		array.push("JohnSkeet");
		assertEquals(10,array.find("JohnSkeet"));
	}
	
	@Test
	public void testFind_afterTenElementsNotContainingSearchedAdded() {
		DynamicArray array = new DynamicArray();
		for (int i=0; i<10; i++)
			array.push("HelloWorld");
		array.push("JohnSkeet");
		assertEquals(-1,array.find("BLABLABLA"));
	}
}
