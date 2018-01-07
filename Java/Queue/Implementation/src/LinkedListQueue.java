//Implementation of a queue using a singly linked list (with both a head and tail pointer)

public class LinkedListQueue {
	
	private static class Node {
		private Node next;
		private Object data;
		
		private Node(Object data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private int size;
	private Node head;
	private Node tail;
	
	public LinkedListQueue() {
		this.size = 0;
		this.head = null;
		this.tail = null;
	}
	
	//Adds the given item to the queue
	//NOTE: happpens at the tail node to prevent a full list traversal when dequeueing
	public void enqueue(Object item) {
		Node nodeToAdd = new Node(item,null);
		if (size == 0) {
			head = nodeToAdd;
			tail = nodeToAdd;
		}
		else {
			tail.next = nodeToAdd;
			tail = nodeToAdd;
		}
		size++;
	}
	
	//Returns value of least recently added item and removes it from the queue
	public Object dequeue() {
		Object value = head.data;
		if (size == 1) {
			head = null;
			tail = null;
		}
		else 
			head = head.next;
		size--;
		return value;
	}
	
	//Returns true if queue has no elements in it and false otherwise
	public boolean isEmpty() {
		return (size==0);
	}
	
	//Returns number of elements in the queue
	public int size() {
		return size;
	}
}