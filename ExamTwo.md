# Object-Oriented Programming Clarifications

## Initialization

### Initialization Block

- Code block to initialize static & instance variables
- Purpose
  - Code copy-pasted in all constructors
  - Useful for anonymous classes (since they don't have constructors)
  - Static initialization block allows complex static variable initialization

```java
  class Foo {
    //static initialization block
    static { A = 1; }

    //instance initialization block
    { B = 2; }
  }
```

```java
  public class Employee {
    private String name;
    private int age;
    private double salary = 100;

    public Employee(int age, String name) {
      //block will be pasted here
      this.age = age;
      this.name = name;
    }

    //instance initialization block
    {
      System.out.println(salary);
      salary = 200;
      System.out.println(salary);
    }

    /*
      prints out:
        100
        200
    */
  }
```

```java
  public class Person {
    private static final Date;

    static {
      Calendar gmtCal = Calendar.getInstance(Timezone.getTimezone("GMT"));
      //...additional date initialization code
    }
  }
```

- Blocks pasted in order of declaration
  - Initializing instance variables above constructor also counts as initialization block
- All initialization statements run before any code you explicitly write in constructor

## Object copying

### Levels

- Reference copy `x = y;`
- Shallow copy `x = y.clone();`
  - If all fields are primitives or immutable types (e.g. `String`), then shallow copy fine
- Deep copy (copies object and each object within that object until you have two truly independent copies)

### Copy constructor

- Constructor that creates a copy of an object of the same class

## Object.clone()

- Creates a shallow copy for you

```java
public class Mouse implements Cloneable {
    @Override
    public Mouse clone() {
        Mouse obj = null;
        //CloneNotSupportedException is a checked exception
        try {
            obj = (Mouse) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
```

- `Cloneable` must be implemented
  - Has no abstract methods associated with it
  - Called a ***marker interface***
    - Just to create isA relationship
- Covariant return type of subtype `Mouse` of `Object`
- Visibility increased from `protected` to `public`
  - `clone()` is declared as `protected` in `Object`

```java
    @Override
    public Computer clone() {
        Computer computer = null;
        try {
            computer = (Computer) super.clone();
            //computer has its own mouse
            if (computer.mouse != null) {
                computer.mouse = mouse.clone();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return computer;
    }
```

```java
  public class SuperComputer extends Computer {
    private StringBuffer users;

    @Override
    public SuperComputer clone() {
      //No need for try-catch since it's dealt with in Computer's clone(), which is being called
      //Computer clone() also does not throw CloneNotSupportedException so no try-catch necessary
      SuperComputer obj = (SuperComputer) super.clone();

      //To also make StringBuffer field independent
      obj.users = new StringBuffer(users);
      return obj;
    }
  }
```

## Garbage Collection

- Reclaiming memory used by unreferenced objects
- JVM has an ***allocation table***
  - Contains info about all objects in heap
  - Knows about local variables that hold references to objects
  - Also knows about references within object referencing something else
    - So no garbage collection if there exists AT LEAST ONE reference (whether via composition or ...)

### Mark and Sweep

- Marks all objects in allocation table as dead
- Follows all references of local variables
  - Marks each object referred to as live
- Sweep allocation table again and remove anything not marked live

#### Mark and Sweep Problems

- Change to heap objects while GC thread is running?
- Stop-the-world
  - Stop application at a safe point, run GC thread, restart

### Memory Leaks

- When you allocate space on the heap, but don't free it up when you no longer need it
- Possible in java if...

```java
  {
    Student[] a = new Student[1000];
    //Processing
    for( ; ;) {
      //Very long for-loop
    }
  }
```

- If in one method then a reference exists as long as method's stack frame is active
  - Local variables stored in stack frame -> disappear after stack frame popped off
- If the loop runs forever (not using array) then a reference persists -> potential memory leak

### Destructor (deprecated)

- Void instance method with name `finalize()`
- No guarantee it will run at all
- Invoked automatically by garbage collector right before object freed
  - To clean up any open file and network connections made by that object

# Algorithmic Time Complexity I

- Algorithm: step-by-step way to solve a problem
- Programming: you know the algorithm and you implement it in a specific language

## Linear Search

- Go through and check if each element is what your searching for or not
- If element found, stop - otherwise, continue searching
- O(n)

## Binary Search

- Assuming elements are in sorted order
- Do what you'd do when searching for word in a physical dictionary
  - Check middle, found then stop, otherwise, continue searching
- O(log<sub>2</sub>(n))

## Algorithmic Efficiency

- Benchmark in terms of time or space
- Tradeoff between space and time
- Options to measure
  - Qualitative: experiment with certain inputs (timing)
    - Too dependent on system running the algorithm
    - You can have a processor 10x as fast
      - But you can also have a problem 10x as big
  - Asymptotic analysis: mathematically analyze efficiency

## Asymptotic Analysis

### Time Function

- T(n) = 10n + 1000
  - T(n) is (unitless) time taken given input size of n (whole number of items)
  - Formula derived by counting number of operations performed in algorithm
  - Key assumption: the more steps an algorithm takes, the more time taken
  - Is an element of the set O(n)

### Big-O Notation

- Check formal definition of Big-O on online slides
- Find the tightest-bound function when finding Big-O
  - Going from CSIC to AV Williams will take you "no less than one hour" etc...
- Big-O only tells you how the algorithm scales
  - Tells you how fast it is by giving you upper bound
  - Does NOT tell you how slow it b/c it is an upper bound
    - Algorithm could be way below upper bound
- Base of the O(log(n)) algorithm class does not matter since base can be manipulated

## Generic Programming

### Generic Class

- `class ArrayList<E>`
  - `ArrayList<String>`
  - `ArrayList<Comparable>`
  - `ArrayList<Integer>` (no primitives allowed)

```java
  public class myGeneric<T> {
    private T value;
    pubic myGeneric(T value) {
      this.value = value;
    }
    public T getValue() {
      return value;
    }
  }
```

- Defining arrays

```java
  T[] data = (T[]) new Object[size];
```

### Generic Subtypes

- If B subtype of A, and GT is a generic type, GT of B NOT subtype of GT of A

```java
  ArrayList<String> stringList = new ArrayList<String>();
  ArrayList<Object> objectList = stringList; //Illegal but let's assume valid
  objectList.add(new Integer(10));
  String entry = stringList.get(0); //Problem!
```

- Works with array, kind of

```java
  Object value = new String("HI");
  String[] stringHolder = new String[1];
  Object[] objectHolder = stringHolder; //Legal
  stringHolder[0] = value; //Will not compile

  /** TropicalFruit extends Fruit **/
  TropicalFruit[] tropicalFruitArray = new TropicalFruit[2];
  Fruit[] fruitArray = tropicalFruitArray; //No problem
  tropicalFruitArray[0] = new Fruit(); //Compilation error
  fruitArray[0] = new Fruit(); //Generates exception
```

### Wildcards

- Collection<?> - collection whose element type matches anything
- Bounded wildcard
  - Upper bound
    - `ArrayList<? extends Shape>`
    - Can assign `ArrayList<Shape>` or `ArrayList<Circle>` (assuming `Circle` extends `Shape`)

```java
  public void printComputers(Collection<Computer> cl) {
    for (Computer c : cl) {
      System.out.println(c);
    }
  }

  public void printObjects(ArrayList<Object> ol) {
    for (Object o : ol) {
      System.out.println(o);
    }
  }

  //Ends up being read only b/c doesn't know what's inside
  public void printAny(ArrayList<?> cl) {
    cl.add(new Integer(10)); //Compilation error
    cl.add(new Object()); //Compilation error (ArrayList<Computer> shouldn't have)
    for (Object c : cl) {
      System.out.println(c);
    }
    Computer cl = cl.get(0); //Compilation error (can only use Object)
  }

  public void printAnyComputer(ArrayList<? extends Computer> cl) {
    cl.add(new Computer("CS",10)); //Compilation error (ArrayList<Laptop> can't have Computer)
    for (Computer c : cl) {
      System.out.println(c);
    }
  }

  public void printPortable(ArrayList<? extends PortableDevice> cl) {
    for (PortableDevice c : cl) {
      System.out.println(c);
    }
  }

  printComputers(new ArrayList<Computer>()); //Works fine
  printObjects(new ArrayList<Computer>()); //Compile error
  printAny(new ArrayList<Computer>()); //Works fine
  printAnyComputer(new ArrayList<Laptop>()); //Works fine since Laptop is-A Computer
  printAnyComputer(new ArrayList<PortableDevice>()); //Compile error since PortableDevice is an interface
  printPortable(new ArrayList<Computer>()); //Works fine since Computer implements the PortableDevice interface
```

- Can have generic methods in non-generic class

### LinkedList

- Arrays stored contiguously in memory and give O(1) access
  - Overhead to resize if necessary
  - Overhead to insert/remove in middle/beginning - O(n)
- LinkedList stored non-contiguously in memory and give O(n) access
  - Very fast insertion and deletion - O(1)

#### Node

- Object with:
  - Generic data field
  - Pointer (next) of static type Node field
    - Self-referential data structure: refers to another instance of itself
    - Next field of last Node instance will be set to null so that iteration stops

```java
  //Enable anything that implements Comparable because compareTo() needs to be called for finding element
  public class MyLinkedList<T extends Comparable<T>> {
    public boolean find(T target) {
      Node temp = head;
      while (temp != null) {
        if (temp.data.compareTo(target) == 0) {
          return true;
        }
        temp = temp.next;
      }
      return false;
    }
  }
```

#### Comparator<T>

- Interface w/ method called compare(T obj1, T obj2)
  - Returns 0 if obj1 and obj2 have same sorting order
  - Returns <0 if obj1 before obj2 in sorting order
  - Returns >0 if obj1 after obj2 in sorting order

```java
  a.compareTo(b); //both a and b must be from classes implementing Comparable<T>
  c.compare(a,b); //c has nothing to do w/ a,b. c is class implementing Comparator<T>
```

### Stack

- Abstract Data Type (implemented w/ Array or LinkedList)
- LIFO queue
  - Last In First Out
  - Insertion and deletion only happen at one end
- Operations
  - Push: add element to top
  - Pop: remove element from top
- Top is a reference pointing to element on top

#### Understandings

- Variables of same name in different stack frames are unique to compiler
- Method parameters AND local variables live in the stack frame
- Java is pass-by-value
  - Variable info duplicated and assigned
- Stack frame = *activation record*
- Stack frames popped during "unwinding" stage of recursive functions

### Dictionary/Map

- Abstract Data Type (ADT)
  - Map
  - Table
  - Associative array
- Key-value pair store
- **Keys should be unique**
  - Adding pair w/ same key updates value
  - Have to make sure same key doesn't exist

#### Array Implementation

- Searching required to find element
- Unsorted
  - Addition: O(n)
  - Removal: O(n)
  - Retrieval: O(n)
  - Traversal: O(n)
- Sorted
  - Addition: O(n)
  - Removal: O(n)
  - Retrieval: O(logn) (binary search)
  - Traversal: O(n)
  - Finding quicker but everyone must move over

#### LinkedList Implementation

- Searching required to find element
- Unsorted
  - Addition: O(n)
  - Removal: O(n)
  - Retrieval: O(n)
  - Traversal: O(n)
- Sorted
  - Addition: O(n)
  - Removal: O(n)
  - Retrieval: O(n)
  - Traversal: O(n)

#### Hash Function

- O(1), constant-time **lookup** operation (no searching required)
- Takes key and outputs array index at which to find value
- Process:
  - Convert search key into integer called hash code
  - Compress hash code into range of indices for hash table
    - Get remainder...(hash code % table size)
- Goals:
  - Minimize collisions
  - Fast to compute

#### Hash Code

```java
  "Java".hashCode();
```

- Calculated by: `74(31)^3 + 97(31)^2 + 118(31) + 97` for "Java"
- Power given by n-1, n-2... where n is lenght of string
- 31 used because it is a prime number
- Default `toString()` prints hash code in hexadecimal
- Hash code itself can be negative but needs to be mapped to nonnegative index eventually

#### Collision Resolution

- Open addressing: find next open index if this one filled
- Separate chaining: make a chain at that index

#### Linear probing

- Resolve collisions by examining consecutive locations until empty spot found
- Value must contain key and value (so value for correct key can be found)
- When you remove, you cannot simply replace w/ `null` (then gaps introduced)

#### Clustering

- 3 states:
  - Occupied: contains reference
  - Empty: location contains `null`, always has
  - Available: open slot, but search should continue until empty hit
- Can enter new values into `null` or empty slots
- When adding, search until next `null` hit to ensure that that entry doesn't already exist
  - If not there, come back to first available and fill it

#### Quadratic Probing

- Instead of searching k+1, k+2... search k+j^2 (so k+1, k+4, k+9)

## Stack Applications

- Infix expression: a + b
- Postfix expression: a b +
- Prefix expression: + a b

## Exam 2

- Part 1: write code that is mostly LinkedList-related (~50%)
  - At least one recursive code question
  - Be comfortable with generics
  - Review WordFrequencyCount.java example for Maps
  - Review HashMap vs. TreeMap vs. LinkedHashMap
    - HashMap: most efficient for lookup
      - Backed up by HashTable
      - Information comes out in arbitrary order
    - TreeMap: used to print items in `compareTo()` (natural) ordering
    - LinkedHashMap: prints out items in order you put them in
    - Methods:
      - map.put(key, value);
      - map.get(key);
      - map.keySet();
- Part 2: analyze the given code and answer questions
- Part 3: short answer (2-3 sentences)
  - Review stack frame diagrams and concepts
- Weeks 6-10
  - Deep copy vs. shallow copy vs. reference copy
  - `clone()` method
  - Garbage collection
  - Initialization blocks (static and instance)
  - Formal definition of Big-O notation (word for word)
    - Review common Big-O notations how they compare
    - Look for dominant term in CMSC 132 to find tightest bound
  - Binary search vs. linear search algorithms
  - TropicalFruit vs. Fruit array example
  - Wildcards
  - Stack ADT
    - Uses:
      - Using stack to evaluate a well-formed expression
      - Translation of infix to postfix operator (book)
  - Map ADT
    - Implementations w/:
      - unsorted array,
      - sorted array,
      - unsorted LinkedList
      - sorted LinkedList
    - See Big-O notations and know why
  - Hashing concept
    - Linear probing

## Exam 2 Key Terms

- Covariant return type
- Marker interface
- Allocation table
- Stop the world
- Mark and sweep
- Collision
- Memory leak: allocating space on heap but never freed up
- `finalize()` supposed to be run by garbage collector before memory freed