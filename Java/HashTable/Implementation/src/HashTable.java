import java.util.Arrays;

//Implementation of a hash table mapping integer keys to values using a fixed-size array

public class HashTable {

	private Object[] arr;
	private int size;
	private int capacity;
	
	public HashTable(int capacity) {
		this.size = 0;
		this.capacity = capacity;
		this.arr = new Object[capacity];
	}
	
	//Inserts key-value pair if key doesn't already exist
	//If key already exists, updates the value
	public void add(int key, Object value) {
		if (size == capacity)
			throw new RuntimeException();
		int index = hash(key);
		if (arr[index] == null)
			arr[index] = value;
		else {
			//Implementation of linear probing
			while (arr[index] != null) {
				index++;
				if (index >= capacity)
					index = 0;
			}
			arr[index] = value;
		}
		size++;
	}
	
	//Returns null if the key isn't associated with a value in the table 
	//Returns the value the key is associated with otherwise
	public Object get(int key) {
		return arr[hash(key)];
	}
	
	//Removes the key if it is is associated with a value in the hashtable and does nothing otherwise
	public void remove(int key) {
		if (!exists(key))
			return;
		arr[hash(key)] = null;
		size--;
	}
	
	//Returns true if the key is associated with a value in the hashtable and false otherwise
	private boolean exists(int key) {
		return (arr[hash(key)] != null);
	}
	
	//Computes hash code of the key
	private int hash(int key) {
		return key % capacity;
	}
	
	//For debugging purposes
	public void printTable() {
		System.out.println(Arrays.toString(arr));
	}
}