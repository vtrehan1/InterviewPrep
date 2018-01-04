//Implementation of the bubble sort algorithm for an unsorted integer array
//Sorts values into ascending order

public class BubbleSort {
	
	public static int[] bubbleSort(int[] arr) {
		int temp = 0;
		int numSwaps = 1;
		
		//If swap is not 1, then there must have been no swaps
		//In this case, the array must already be sorted so sorting stops
		for (int i=arr.length-1; i>0 && (numSwaps==1); i--) {
			numSwaps = 0;
			for (int j=1; j<=i; j++) {
				if (arr[j-1] > arr[j]) {
					temp = arr[j];
					arr[j] = arr[j-1];
					arr[j-1] = temp;
					numSwaps = 1;
				}
			}
		}
		return arr;
    }
}
