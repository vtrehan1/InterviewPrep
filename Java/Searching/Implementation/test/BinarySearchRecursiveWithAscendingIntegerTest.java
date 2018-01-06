import static org.junit.Assert.*;

import org.junit.Test;
public class BinarySearchRecursiveAscendingIntegerTest {

	@Test
	public void testBinarySearch_normalArrayWithFiveElements() {
		int[] arr = {1,2,6,7,10};
		assertEquals(2, BinarySearchRecursiveAscendingInteger.binarySearch(arr,6));
	}
	
	@Test
	public void testBinarySearch_normalArrayWithSevenElements() {
		int[] arr = {1,2,6,7,10,13,15};
		assertEquals(-1, BinarySearchRecursiveAscendingInteger.binarySearch(arr,3));
	}
	
	@Test
	public void testBinarySearch_normalArrayWithNineElements() {
		int[] arr = {1,2,6,7,10,13,15,17,21};
		assertEquals(-1, BinarySearchRecursiveAscendingInteger.binarySearch(arr,108));
	}
	
	@Test
	public void testBinarySearch_normalArrayWithOneElement() {
		int[] arr = {1};
		assertEquals(-1, BinarySearchRecursiveAscendingInteger.binarySearch(arr,108));
	}
	
	@Test
	public void testBinarySearch_arrayWithLengthZero() {
		int[] arr = {};
		assertEquals(-1, BinarySearchRecursiveAscendingInteger.binarySearch(arr,1));
	}
}
