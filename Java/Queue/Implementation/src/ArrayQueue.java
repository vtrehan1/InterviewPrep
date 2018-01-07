//Implementation of a queue using a fixed-size array

public class ArrayQueue {
	
	private final int capacity;
	private int size;
	private int head;
	private int tail;
	private Object[] arr;
	
	public ArrayQueue(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.head = -1;
		this.tail = -1;
		
		arr = new Object[capacity];
	}
	
	//Adds the given item to the queue
	public void enqueue(Object item) {
		if (size == 0) {
			head = 0;
			tail = 0;
			arr[0] = item;
		}
		else {
			tail++;
			if (tail >= capacity)
				tail = 0;
			arr[tail] = item;
		}
		size++;
	}
	
	//Returns the value of the least recently added item and removes the item from the queue
	public Object dequeue() {
		Object value = arr[head];
		arr[head] = null;
		if (size == 1) {
			head = 0;
			tail = 0;
		}
		else {
			head++;
			if (head >= capacity)
				head = 0;
		}
		size--;
		return value;
	}
	
	//Returns true if queue has no elements in it and false otherwise
	public boolean isEmpty() {
		return (size==0);
	}
	
	//Returns true if number of elements in queue equals total capacity and false otherwise
	public boolean isFull() {
		return (size==capacity);
	}
	
	//Returns number of elements in the queue
	public int size() {
		return size;
	}
}