//Implementing a dynamic, resizable array

public class DynamicArray {

    //Stores number of items in the array
    private int size;
    
    //Stores total number of items array can hold
    private int capacity;

    //Points to the array represented by this instance
    private Object[] arr;

	public DynamicArray() {
        this.size = 0;
        this.capacity = 16;
        this.arr = new Object[this.capacity];
    }

    public DynamicArray(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.arr = new Object[this.capacity];
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.capacity;
    }

    public boolean isEmpty() {
        return (this.size == 0);
    }

    //Returns value at the given index
    //Blows up if index is out of bounds
    public Object get(int index) {
        if (index < 0 || index >= this.size)
            throw new ArrayIndexOutOfBoundsException();
        return this.arr[index];
    }

    //Updates value at a particular index
    //Blows up if that index doesn't already have a value
    public void update(int index, Object item) {
    	if (index < 0 || index >= this.size)
            throw new ArrayIndexOutOfBoundsException();
        this.arr[index] = item;
    }

    //Adds an item to the end of the array
    public void push(Object item) {
        if (this.size == this.capacity)
            resize(2*this.capacity);
        this.arr[size] = item;
        this.size++;
    }

    //Inserts the item at the specified index
    //Trailing indices are shifted right
    public void insert(int index, Object item) {
        if (index < 0 || (index >= this.size && index != 0))
            throw new ArrayIndexOutOfBoundsException();
        if (this.size == this.capacity)
            resize(2*this.capacity);
        for (int i=this.size; i>index; i--)
            this.arr[i] = this.arr[i-1];
        this.arr[index] = item;
        this.size++;
    }
    
    //Inserts the item at index 0
    public void prepend(Object item) {
        insert(0,item);
    }
    
    //Deletes element at the index
    //Shifts all trailing elements left
    public void delete(int index) {
        if (index < 0 || index >= this.size)
            throw new ArrayIndexOutOfBoundsException();
        for (int i=index; i<size-1; i++)
            this.arr[i] = this.arr[i+1];
        this.arr[this.size-1] = null;
        this.size--;
    }
    
    //Removes and returns the last element 
    public Object pop() {
        if (this.size == 0)
            throw new ArrayIndexOutOfBoundsException();
        Object endVal = this.arr[this.size-1];
        delete(this.size-1);
        if (this.size <= (int) 0.25*this.capacity)
            resize((int) 0.5*this.capacity);
        return endVal;
    }
    
    //Removes each index holding the item
    public void remove(Object item) {
        for (int i=0; i<this.size; i++) {
            if (this.arr[i].equals(item)) {
                delete(i);
                i--;
            }
        }
    }
    
    //Looks for item and returns index of first occurrence
    //Returns -1 if not found
    public int find(Object item) {
        for (int i=0; i<this.size; i++) {
            if (this.arr[i].equals(item)) 
                return i;
        }
        return -1;	
    }
    
    //Handles array resizing
    private void resize(int new_capacity) {
        Object[] arrResized = new Object[new_capacity];
        for (int i = 0; i < this.arr.length; i++)
            arrResized[i] = this.arr[i]; 
        this.capacity = arrResized.length;
        this.arr = arrResized;
    }
 }