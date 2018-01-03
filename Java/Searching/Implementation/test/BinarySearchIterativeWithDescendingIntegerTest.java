import static org.junit.Assert.*;

import org.junit.Test;
public class BinarySearchIterativeDescendingIntegerTest {

	@Test
	public void testBinarySearch_normalArrayWithFiveElements() {
		int[] arr = {10,7,6,2,1};
		assertEquals(2, BinarySearchIterativeAscendingInteger.binarySearch(arr,6));
	}
	
	@Test
	public void testBinarySearch_normalArrayWithSevenElements() {
		int[] arr = {15,13,10,7,6,2,1}; 
		assertEquals(-1, BinarySearchIterativeAscendingInteger.binarySearch(arr,3));
	}
	
	@Test
	public void testBinarySearch_normalArrayWithNineElements() {
		int[] arr = {21,17,15,13,10,7,6,2,1}; 
		assertEquals(-1, BinarySearchIterativeAscendingInteger.binarySearch(arr,108));
	}
	
	@Test
	public void testBinarySearch_normalArrayWithOneElement() {
		int[] arr = {1};
		assertEquals(-1, BinarySearchIterativeAscendingInteger.binarySearch(arr,108));
	}
	
	@Test
	public void testBinarySearch_arrayWithLengthZero() {
		int[] arr = {};
		assertEquals(-1, BinarySearchIterativeAscendingInteger.binarySearch(arr,1));
	}

}

