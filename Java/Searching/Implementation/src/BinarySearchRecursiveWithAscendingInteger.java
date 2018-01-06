//Implementation of recursive binary search on an array of integers sorted in ascending order

public class BinarySearchRecursiveAscendingInteger {

	public static int binarySearch(int[] arr, int value) {
		if (arr.length == 0)
			return -1;
		
		int low = 0;
		int high = arr.length -1;
		int midpoint = 0;
		
		return findElement(arr, value, low, high, midpoint);
	}

	private static int findElement(int[] arr, int value, int low, int high, int midpoint) {
		if (low >= high)
			return -1;
		midpoint = (low+high)/2;
		if (arr[midpoint] == value)
			return midpoint;
		if (arr[midpoint] > value)
			return findElement(arr,value,midpoint+1,high,midpoint);
		else 
			return findElement(arr,value,low,midpoint-1,midpoint);
	}
}