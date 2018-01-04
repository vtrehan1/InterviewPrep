//Implementation of merge sort for an unsorted array of integers
//Sorts values in increasing order 

public class MergeSort {
	
	public static int[] sort(int[] arr) {
		int[] tempArr = new int[arr.length];
		mergeSort(arr,tempArr,0,arr.length-1);
		return arr;
	}

	private static void mergeSort(int[] arr, int[] tempArr, int low, int high) {
		if (low >= high)
			return;
		int middle = (low+high)/2;
		mergeSort(arr,tempArr,low,middle);
		mergeSort(arr,tempArr,middle+1,high);
		merge(arr,tempArr,low,middle,high);
	}

	private static void merge(int[] arr, int[] tempArr, int low, int middle, int high) {
		int firstIndex = low;
		int firstMax = middle;
		int secondIndex = middle+1;
		int secondMax = high;	
		
		int position = low;
		
		while (firstIndex <= firstMax && secondIndex <= secondMax) {
			if (arr[firstIndex] < arr[secondIndex])
				tempArr[position++] = arr[firstIndex++];
			else
				tempArr[position++] = arr[secondIndex++];
		}
		
		while (firstIndex <= firstMax) {
			tempArr[position++] = arr[firstIndex++];
		}
		
		while(secondIndex <= secondMax) {
			tempArr[position++] = arr[secondIndex++];
		}
		
		for (int j=low; j<=high; j++) {
			arr[j] = tempArr[j];
		}
	}
}
