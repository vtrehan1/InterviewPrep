//Implementation of a singly linked list and some of its common methods
//Includes both head and tail pointers

public class SinglyLinkedList {
	
	private static class Node {
		Object data;
		Node next;
		
		//Instantiates node with the given data and pointer to next node
		private Node(Object data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private int size;
	private Node head;
	private Node tail;
	
	//Instantiates list with head/tail pointers
	public SinglyLinkedList() {
		this.size = 0;
		this.head = null;
		this.tail = null;
	}
	
	//Returns number of elements in list
	public int size() {
		return this.size;
	}
	
	//Returns true if list has no elements in it, false otherwise
	public boolean isEmpty() {
		return (this.size == 0);
	}
	
	//Returns value of nth item (starting at 0 for first)
	public Object valueAt(int index) {
		Node nodeIterator = head;
		for (int i=0; i<index; i++)
			nodeIterator = nodeIterator.next;
		return nodeIterator.data;
	}
	
	//Adds an item to the front of the list
	public void pushFront(Object item) {
		Node firstNode;
		if (size == 0) {
			firstNode = new Node(item,null);
			head = firstNode;
			tail = firstNode;
		}
		else {
			firstNode = new Node(item, head);
			head = firstNode;
		}
		size++;
	}
	
	//Remove front item and return its value
	public Object popFront() {
		Object value = head.data;
		if (size == 1) {
			tail = null;
			head = null;
		}
		else {
			head = head.next;
		}
		size--;
		return value;
	}
	
	//Adds an item at the end of the list
	public void pushBack(Object item) {
		Node lastNode = new Node(item,null);
		if (size == 0)
			head = lastNode;
		else
			tail.next = lastNode;
		tail = lastNode;
		size++;
	}
	
	//Removes end item and returns its value
	public Object popBack() {
		Object value = tail.data;
		if (size == 1) {
			tail = null;
			head = null;
		}
		else {
			Node nodeIterator = head;
			for (int i=0; i<size-1; i++)
				nodeIterator = nodeIterator.next;
			tail = nodeIterator;
		}
		size--;
		return value;
	}
	
	//Get value of front item
	public Object frontVal() {
		return head.data;
	}
	
	//Get value of front item
	public Object backVal() {
		return tail.data;
	}
	
	//Insert value at index, so current item at that index is pointed to by new item at index
	public void insert(Object item, int index) {
		Node nodeIterator = head;
		Node nodeToInsert;
		if (size == 0 && index == 0) {
			nodeToInsert = new Node(item,null);
			nodeToInsert = head;
			nodeToInsert = tail;
		}
		else {
			for (int i=0; i<index-1; i++) 
				nodeIterator = nodeIterator.next;
			nodeToInsert = new Node(item, nodeIterator.next);
			nodeIterator.next = nodeToInsert;
		}
		size++;
	}
	
	//Removes node at the given index
	public void erase(int index) {
		if (size == 1 && index == 0) {
			head = null;
			tail = null;
		}
		Node nodeIterator = head;
		if (index == size-1) {
			nodeIterator = tail;
			nodeIterator.next = null;
		}
		else {
			for (int i=0; i<index-1; i++) 
				nodeIterator = nodeIterator.next;
			Node temp = nodeIterator.next;
			nodeIterator.next = temp.next;
			temp.next = null;
		}
		size--;
	}
	
	//Returns value of node at nth position from end of list
	public Object valueNfromEnd(int n) {
		int index = size - 1 - n;
		Node nodeIterator = head;
		for (int i=0; i<index; i++)
			nodeIterator = nodeIterator.next;
		return nodeIterator.data;
	}
	
	//Removes first item in the list with given the value
	public void removeValue(Object item) {
		Node nodeIterator = head;
		for (int i=0; i<size-1; i++) {
			nodeIterator = nodeIterator.next;
			if (nodeIterator.data.equals(item)) {
				erase(i);
				return;
			}
		}
		size--;
	}
	
	//For debugging purposes
	public void printList() {
		Node nodeIterator = head;
		int count = 0;
		while (nodeIterator != null && count<size) {
			System.out.printf("Number at %d is %s\n", count, (String) nodeIterator.data); 
			count++;
			nodeIterator = nodeIterator.next;
		}
	}
}
