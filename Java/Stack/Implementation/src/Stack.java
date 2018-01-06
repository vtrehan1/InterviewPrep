//Implementation of the stack data type using a singly linked list

public class Stack {

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
	
	public Stack() {
		this.size = 0;
		this.head = null;
	}
	
	//Pushes the specified item onto the stack
	public void push(Object item) {
		Node nodeToAdd;
		if (size == 0) 
			nodeToAdd = new Node(item,null);
		else
			nodeToAdd = new Node(item,head);
		head = nodeToAdd;
		size++;
	}
	
	//Removes and returns the top element of the stack
	public Object pop() {
		Object value = head.data;
		head = head.next;
		size--;
		return value;
	}
	
	//Returns the value of the top element of the stack, but doesn't remove it
	public Object top() {
		return head.data;
	}
	
	//Returns true of the stack has no elements in it and false otherwise
	public boolean isEmpty() {
		return (size == 0);
	}
	
	//Returns the size of the stack
	public int size() {
		return size;
	}
}