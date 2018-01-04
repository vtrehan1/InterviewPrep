import static org.junit.Assert.*;

import org.junit.Test;

public class MergeSortTest {

	@Test
	public void testMergeSort_emptyArray() {
		int[] test = {};
		assertArrayEquals(test,MergeSort.sort(test));
	}
	
	@Test
	public void testBubbleSort_oneElementArray() {
		int[] test = {1};
		assertArrayEquals(test,MergeSort.sort(test));
	}
	
	@Test
	public void testBubbleSort_fiveElementArray() {
		int[] test = {2,1,3,5,4};
		int[] result = {1,2,3,4,5};
		assertArrayEquals(result,MergeSort.sort(test));
	}
	
	@Test
	public void testBubbleSort_tenElementArray() {
		int[] test = {8,1,9,5,4,10,6,3,2,7};
		int[] result = {1,2,3,4,5,6,7,8,9,10};
		assertArrayEquals(result,MergeSort.sort(test));
	}
}


