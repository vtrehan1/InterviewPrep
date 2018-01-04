import static org.junit.Assert.*;

import org.junit.Test;

public class InsertionSortTest {

	@Test
	public void testInsertionSort_emptyArray() {
		int[] test = {};
		assertArrayEquals(test,InsertionSort.insertionSort(test));
	}
	
	@Test
	public void testInsertionSort_oneElementArray() {
		int[] test = {1};
		assertArrayEquals(test,InsertionSort.insertionSort(test));
	}
	
	@Test
	public void testInsertionSort_fiveElementArray() {
		int[] test = {2,1,3,5,4};
		int[] result = {1,2,3,4,5};
		assertArrayEquals(result,InsertionSort.insertionSort(test));
	}
	
	@Test
	public void testInsertionSort_tenElementArray() {
		int[] test = {8,1,9,5,4,10,6,3,2,7};
		int[] result = {1,2,3,4,5,6,7,8,9,10};
		assertArrayEquals(result,InsertionSort.insertionSort(test));
	}
}

