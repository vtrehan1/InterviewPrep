//Implementation of quick sort on an unsorted array of integers
//Sorts values in ascending order
public class QuickSort {
	
	public static int[] sort(int[] nums) {
		quickSort(nums,0,nums.length-1);
		return nums;
	}

	private static void quickSort(int[] arr, int start, int end) {
		if (start >= end)
			return;
		int pIndex = end;
		int pivot = arr[end];
		int temp = 0;
		int j = start-1;
		for (int i=start; i<=end; i++) {
			if (arr[i] <= pivot) {
				temp = arr[i];
				arr[i] = arr[++j];
				arr[j] = temp;
				
				if (i == pIndex) 
					pIndex = j;
			}
		}
		quickSort(arr,start,pIndex-1);
		quickSort(arr,pIndex+1,end);
	}
}
