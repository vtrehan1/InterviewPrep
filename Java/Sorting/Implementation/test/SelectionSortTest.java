import static org.junit.Assert.*;

import org.junit.Test;

public class SelectionSortTest {

	@Test
	public void testBubbleSort_emptyArray() {
		int[] test = {};
		assertArrayEquals(test,BubbleSort.bubbleSort(test));
	}
	
	@Test
	public void testBubbleSort_oneElementArray() {
		int[] test = {1};
		assertArrayEquals(test,BubbleSort.bubbleSort(test));
	}
	
	@Test
	public void testBubbleSort_fiveElementArray() {
		int[] test = {2,1,3,5,4};
		int[] result = {1,2,3,4,5};
		assertArrayEquals(result,BubbleSort.bubbleSort(test));
	}
	
	@Test
	public void testBubbleSort_tenElementArray() {
		int[] test = {8,1,9,5,4,10,6,3,2,7};
		int[] result = {1,2,3,4,5,6,7,8,9,10};
		assertArrayEquals(result,BubbleSort.bubbleSort(test));
	}
}
