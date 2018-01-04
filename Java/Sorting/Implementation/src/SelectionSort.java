//Implementation of the selection sort algorithm for an unsorted integer array
//Sorts values into ascending order

public class SelectionSort {

	public static int[] selectionSort(int[] arr) {
		int temp = 0;
		int min = -1;
		int swap = 1;
		for (int i=0; i<arr.length-1 && (swap==1); i++) {
			min = i; 
			swap = 0;
			for (int j=i+1; j<arr.length; j++) {
				if (arr[j] < arr[min])
					min = j;
					swap = 1;
			}
			temp = arr[i];
			arr[i] = arr[min];
			arr[min] = temp;
		}
		return arr;
	}
}
