//Implementation of iterative binary search on an array of integers sorted in ascending order

public class BinarySearchIterativeAscendingInteger {
	
	//Returns index of value to find or -1 if it cannot be found
	public static int binarySearch(int[] arr, int value) {
		if (arr.length == 0)
			return -1;
		
		int low = 0;
		int high = arr.length-1;
		int midpoint = -1;

		while(true) {
			if (high == low)
				break;
			midpoint = (low+high)/2;
			if (arr[midpoint] == value)
				return midpoint;
			if (arr[midpoint] > value)
				low = midpoint+1;
			else
				high = midpoint-1;
		}
		return -1;
	}
}
