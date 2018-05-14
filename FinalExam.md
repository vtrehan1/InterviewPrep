# Final Exam: CMSC 132

## Abstraction

- Procedural abstraction: specifies actions and hides algorithms (sorting -> bubble sort? selection sort?)
- Data abstraction: specify the data and hide representation (list of names -> ArrayList? LinkedList?)
- Abstract data type: interfaces defining the behavior of a data structure (no implementation)

## Top Level Interfaces

### General

- Specifies a contract -> defines behaviors implementing class needs to implement
- Method types:
  - Abstract: no implementation
  - Default: has default code, can write your own implementation if you want
- Methods are `public` by default, no `private` methods are allowed

### Critical

- Provide a unidirectional is-A relationship
- Cannot be instantiated: only classes implementing them can be instantiated

### Gotchas

- Assigning object to interface static type limits callable methods to those defined in interface
  - Casting required to call class-specific methods
- Multiple interfaces can be implemented by a class (used to fake multiple inheritance)
- Interfaces can extend multiple interfaces (class must implement methods in ALL interfaces)

## Key Interfaces

### Iterable T

- Must implement this interface to get enhanced for loop
- Must implement `public Iterator<T> iterator()` method

### Iterator T

- Methods to implement:
  - `public boolean hasNext()`: structure iterating through has next (by your definition of next)
  - `public T next()`: returns next element (by your definition of next)
- `hasNext()` should be able to be called any number of times without affecting what is next
  - Do all pointer incrementing inside of the `next()` method

```java
    // Commit to actual class like String when defining
    public class RandomClass implements Iterable<Integer> {

        private int[] elements = new int[]{ 1, 2, 3, 4, 5 };

        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {

                private int pos = 0;

                public boolean hasNext() {
                    return pos < elements.length;
                }

                // Incrementing counter is done in next() NOT hasNext()
                public Integer next() {
                    return elements[pos++];
                }
            };
        }
    }

    public class Driver {

        public static void main(String[] args) {

            RandomClass rc = new RandomClass();

            // Static type of item has to match type of T in Iterable<T>
            for (Integer item : rc) {
                System.out.print(item);
            }

            // prints 12345
        }
    }
```

### Comparable T

- Must implement the `public int compareTo(T other)` method
  - Returning >0 -> `this` > other
  - Return <0 -> `this` < other
  - Returning 0 -> `this` = other
- Consistency with `equals()` when returning 0 is strongly recommended

```java
    // Remember to include the Dog in Comparable<Dog>
    public class Dog implements Comparable<Dog> {

        // Sorts in order of increasing height
        public int compareTo(Dog other) {
            if (this.height > other.height) {
                return 1;
            }
            if (this.height < other.height) {
                return -1;
            }
            return 0;
        }
    }
```

## Enumerated Types

- Used when you know all of the possible values of the data
- E.g. storing card suits since there's only 4 possible values
  - Diamond, Club, Heart, Spade

```java
    public enum Suit {
        // Values accessed like Suit.DIAMOND etc...
        DIAMOND, CLUB, HEART, SPADE;
    }
```

## Inheritance

### General

- Derived class/sub class/child class from base class/super class/parent class
- Creates unidirectional is-A relationship between sub class and super class
- All instance fields and public methods inherited by child class
  - Private methods and constructors not inherited

### Super

- Used in constructor
  - `super` call must be first line in child class constructor
    - Implicitly invoked on default constructor in parent class if not written explicitly
    - If base class has defined a non-default constructor, `super()` must be invoked explicitly
    - Since `this()` also has to be the first line in the constructor, cannot use them both
- Used to call overriden parent class methods (i.e. `super.toString()`)
- Used to access overriden instance fields (i.e. `super.name`)

### Overriding

- Giving method in parent class new definition in child class
- Options:
  - Maintain same method signature -> same name, access modifier, return type, parameter types
  - Use covariant return type: subtype of return type of parent class method
  - Widen access modifier: changing more restrictive access modifier to less restrictive one

#### equals()

- `equals()` in `Object` class has same definition as `==`
  - Checks if two references point to same memory location

```java
    public class Dog {

        private int height;

        // Method signature must match definition in Object
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Dog)) {
                return false;
            }
            Dog other = (Dog) object;

            // Custom definition of equality
            return this.height == other.height;
        }
    }
```

#### toString()

- Default `toString()` in `Object` returns hex value of object's memory location
- Implicitly invoked in `System.out.println()` statement

```java
    public String toString() {
        return "Height: " + height;
    }
```

#### clone()

- Default `clone()` in `Object` class creates shallow copy of object
- To create deep copy:

```java
    public class Computer implements Cloneable {

        // Assume this Mouse class has no composition
        private Mouse mouse;

        // clone() in Object defined as protected Object clone()
        public Computer clone() {
            Computer copy = null;
            try {
                copy = (Computer) super.clone();
                if (mouse != null) {
                    copy.mouse = mouse.clone();
                }
            } catch (CloneNotSupportedException e) {
                // Do some exception handling
            }
            return copy;
        }
    }

    // No need to implement Cloneable or do try-catch since superclass already handles it
    public class SuperComputer extends Computer {

        private StringBuffer text;

        // Overriding clone in Computer class
        public SuperComputer clone() {
            SuperComputer copy = (SuperComputer) super.clone();
            copy.text = new StringBuffer(text);
            return copy;
        }
    }
```

- `Cloneable` is a marker interface (has no methods) that must be implemented to override `clone()`

### Gotchas

- Private data can only be accessed through setters/getters

```java
    public class SuperClass {
        public void printHi() {
            System.out.println("hi!");
        }
    }

    public class SubClass extends SuperClass {
        public void printHey() {
            System.out.println("hey!");
        }
    }

    SuperClass subclass = new SubClass();
    subclass.printHey(); // won't compile, static type is SuperClass -> can only call printHi()
```

- `super` can access all overriden instance variables
- `super` can only access overriden methods in immediate parent class

```java
  class A {
    int x = 10;

    public void f() {
      System.out.println('Hiya');
    }
  }
  class B extends A {
    int x = 15;

    public void f() {
      System.out.println('Hi');
    }
  }
  class C extends B {
    int x = 5;

    public void f() {
      System.out.println('Hello');
      super.f() //prints Hi
    }

    public void printX() {
      System.out.println(super.x); //prints 15
      System.out.println((A (this)).x); //prints 10
    }

    // Cannot print Hiya since cannot access f() in A
  }
```

- Assigning object to superclass static type restricts methods that can be called
  - Must cast to call subclass specific methods

```java
    public class Base {
        public void doSomething(int x) {}
    }

    public class Derived extends Base {
        public void doSomething(double x) {}

        public static void main (String[] args) {
            Base b = new Derived();
            b.doSomething(8.0); //will not compile!
        }
    }
```

## Access Modifiers

- Least restrictive to most restrictive:
  - `public`: anyone can access
  - `protected`: all derived classes and classes in same package have direct access
  - package: only classes inside the same package have direct access
  - `private`: only class inside which it's declared has direct access
- Top-level classes can only be `public` or have package access

## Early/Late Binding

- Early binding (C++):
  - Calling method on reference checked at compile time
  - Executes method version in static type class at runtime
- Late binding (Java):
  - Calling method on reference checked at compile time
  - Executes method version of dynamic type class at runtime

```java
  // Assume Faculty extends Person

  Faculty a = new Faculty();
  Person b = a;
  System.out.println(b.toString());

  // Early binding --> calls Person version of toString()
  // Late binding --> calls Faculty version of toString()
```

## Reference assignment

- Object of static type of subclass can be assigned to reference of static type of superclass
- Object of static type of class can be assigned to reference of static type of interface

```java
  // Assume Faculty extends Person

  Faculty a = new Faculty();
  Person b = a; // allowed since Person is superclass of Faculty

  Person c = new Person();
  Faculty d = c; // not allowed since Faculty is NOT superclass of Person
```

- Strategy: ask if right is-A left

## Overloading

- Changing number/type(s) of parameters of a method

## Composition

- Having instance fields of a class be references of static types of other classes
- Creates a unidirectional has-A relationship 

```java
    public class Hello {
        String string;

        // Composition since String is a class
    }
```

## Casting Safety

- `x.getClass()` returns runtime class (dynamic type) of object reference
- `x instanceof y` checks if x is-A y
  - y can be a superclass or interface

## Abstract Classes

- Cannot be instantiated
- Extended by subclasses which implement their `abstract` methods

### Gotchas

- If `abstract` method exists, class must be abstract
- `abstract` class doesn't have to have `abstract` methods
- Neither `abstract` classes nor methods can be declared final
- Subclass doesn't implement parent's `abstract` methods -> it too must be abstract

## Final Keyword

- Final classes cannot be extended
- Final methods cannot be overriden
- Final variables cannot be reassigned after initialization

## Exceptions

### General

- All exceptions inherit from `Throwable` class
- `Exception` and `Error` inherit from `Throwable`

### Checked vs. Unchecked

- Unchecked
  - All exceptions inheriting from `RuntimeException` and `Error` unchecked
  - No need to deal w/ exception (e.g. don't have to write try-catch or throws)
- Checked
  - All exceptions inheriting from `Exception` are checked
  - Must deal w/ exception (either try-catch or throws)

### Gotchas

- Even if try has a `return`, `finally` executes and then `return` happens
- `catch` blocks must be ordered from specific to general

## Nested Types

### Types

- **Inner**: class declared inside class and doesn't have keyword `static`
- **Local**: class declared inside method
- **Anonymous**: local class w/o a name
- **Static**: class declared `static` inside a class

### Usage

- Nested classes localize code to part where needed
- Nested classes break encapsulation
  - Nested + outer class have direct access to each other's fields/methods, even if `private`

### Inner class

- Inner class instance always tied to instance of outer class
  - Can only access outer class's nonstatic fields
- Can have any of the 4 access modifiers

```java
  public class MyOuterClass {
    private int x;

    private class MyInnerClass {
      private int y;
      void foo() {
          x = 1; //accessing private field
      }
    }

    void bar() {
      MyInnerClass ic = new MyInnerClass();
      ic.y = 2; //accessing private field
    }
  }
```

### Local classes

- Only method inside which declaration happens can instantiate local class object
- Only allowed to have package access when declared
- Access:
  - To outer class member variables
  - To local `final` variables
  - To effectively final variables as per Java 8
    - Variables not declared `final` but whose values aren't changed over method execution

```java
  public Iterator<Player> iterator() {
    class TeamIterator implements Iterator<Player> {
      //some implementation
    }
    return new TeamIterator();
  }
```

### Anonymous class

- No constructor allowed since class doesn't have a name (use initialization block instead)
- Reference has static type of interface or superclass

```java
  public Iterator<Player> iterator() {
    return new Iterator<Player>() {
        //...
    }; //semicolon important
  }
```

```java
  public class Tetris {}

  public class Driver {
    public static void main(String[] args) {
      Tetris superTetris = new Tetris() {
        public void dropPiece() {

        }
      };
      //Anonymous class extends Tetris
    }
  }
```

### Static class

- Can only access `static` variables in outer class (even if `private`)
- NOT tied to instance of the outer class
- Can use all 4 access modifiers

```java
  public class Outer {
    public int x;
    static int y = 2;

    public Outer() {
      x = 1;
    }

    public static class nestedExample {
      int z;
      void someMethod() {
        //Will NOT compile, can't access nonstatic members
        x = 5;

        //Direct access to static outer class fields
        y = 20;
      }
    }
  }

  public class Driver {
    public static void main(String[] args) {
      //Not tied to outer class instance
      OuterClass.nestedExample nE = new OuterClass.nestedExample();
    }
  }
```

### Gotchas

```java
  public class MyOuter {
    int x = 2;
    private class MyInner {
      int x = 6;
      private void getX() {
        int x = 8;
        System.out.println(x); //prints 8
        System.out.println(this.x); //prints 6
        System.out.println(MyOuter.this.x); //prints 2
      }
    }
  }
```

```java
  public class Skywalker {
    public class Offspring {}
  }
  public class Driver {
    public static void main(String[] args) {
      Skywalker vader = new Skywalker();

      //Instantiating inner class object from outside outer class
      Skywalker son = vader.new Offspring();

      //Offspring is a MEMBER of Skywalker
      Skywalker.Offspring son1 = vader.new Offspring();

      //Can never do the following
      Skywalker son = new Offspring();
    }
  }
```

### Lambda Expression

- Concise approach to define anonymous class instance
- Only for functional interfaces: interfaces w/ only one abstract, unimplemented method
  - Static type is the type of the interface

```java
  Task anonymousVersion = new Task() {
    public int compute(int x) {
      return x + x;
    }
  };

  // No need to have type of method parameters
  Task lambdaVersion = (int x) -> {
      return x + x;
  };

  //If body of the method is one-liner and method returns x + x
  Task anotherLambdaVersion = (x) -> x + x;

  //If method takes single argument and body of the method returns x + x
  Task yetAnotherLambdaVersion = x -> x + x;

  //If method takes no arguments and body of the method returns 10
  Another yetAnotherLambdaVersion = () -> 10;
```

## Initialization

### Usage

- Copy-paste code into all constructors (esp. in anonymous class)
- Perform complex initialization of static variables

### Gotchas

- All initialization blocks (including constructor) executed in order of declaration

```java
  public class Employee {
    private String name;
    private int age;
    private double salary = 100; // Executed before constructor

    public Employee(int age, String name) {
      this.age = age;
      this.name = name;
    }

    // Instance initialization block
    {
      System.out.println(salary);
      salary = 200;
      System.out.println(salary);
    }

    /*
      Prints out:
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
      // ...additional date initialization code
    }
  }
```

## Copying

- Reference copy: return alias to same object
- Shallow copy: separate objects but share fields
- Deep copy: separate objects w/ completely separate fields

## Garbage Collection

- Object on heap eligible for garbage collection when no active references to it
- JVM has allocation table with list of all object references
  - Stops everything at stop-the-world point and executes mark-and-sweep
    - Mark everything dead on first pass
    - Using allocation table mark all objects w/ active references live
    - Sweep through again and remove all objects that are still marked dead

## Big-Oh Notation

- If constants M, No exist s.t. f(n) <= M*g(n) for all N >= No, f(n) = O(g(n))
- Analyze the algorithm's critical section: portion contributing most to complexity

## Generics

### Wildcards

- ArrayList<?>: any class is valid as the generic type
- ArrayList<? extends [insert class]>: class or any of its subclasses are valid
- ArrayList<? extends [insert interface]>: interface or classes implementing interface are valid

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
    cl.add(new Object()); //Compilation error (ArrayList<Computer> shouldn't have Object)

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

  printComputers(new ArrayList<Computer>()); // Works fine
  printObjects(new ArrayList<Computer>()); // Compile error
  printAny(new ArrayList<Computer>()); // Works fine
  printAnyComputer(new ArrayList<Laptop>()); // Works fine since Laptop is-A Computer
  printAnyComputer(new ArrayList<PortableDevice>()); // Compile error since PortableDevice is interface
  printPortable(new ArrayList<Computer>()); // Works fine, Computer implements PortableDevice interface
```

### Gotchas

- If A is a subtype of B, Generic(A) is NOT subtype of Generic(B)

```java
  ArrayList<Person> personList = new ArrayList<>();
  ArrayList<Object> objectList = personList; // compilation error
```

```java
  // Assume Faculty extends Person

  ArrayList<Faculty> facultyList = new ArrayList<>();
  ArrayList<? extends Person> personList = facultyList;

  // Allowed since null can be added to any ArrayList regardless of generic type
  personList.add(null);
```

```java
  TropicalFruit[] tropicalFruitArray = new TropicalFruit[10];

  // Allowed but causes problems
  FruitArray[] fruitArray = tropicalFruitArray;

  tropicalFruitArray[0] = new TropicalFruit(); // allowed
  tropicalFruitArray[0] = new Fruit(); // compilation error
  fruitArray[0] = new TropicalFruit(); // allowed
  fruitArray[0] = new Fruit(); // throws exception
```

## Comparator T

- External class used to sort (e.g.`c.compare(a,b)`)
  - If >0 returned, a > b
  - If <0 returned a < b
  - If 0 returned a = b

## Stack

- Stack frame = activation record
- LIFO (last in first out) queue
- Key operations: pushing and popping

## Dictionary/Map

- Key-value pair with unique (no duplicates) search keys

### Implementations

- LinkedList:
  - Sorted
    - Addition: O(n)
    - Removal: O(n)
    - Retrieval: O(n)
    - Traversal: O(n)
  - Unsorted
    - Addition: O(n)
    - Removal: O(n)
    - Retrieval: O(n)
    - Traversal: O(n)
- Array
  - Sorted
    - Addition: O(n)
    - Removal: O(n)
    - Traversal: O(n)
    - Retrieval: O(logn) (binary search)
  - Unsorted
    - Addition: O(n)
    - Removal: O(n)
    - Retrieval: O(n)
    - Traversal: O(n)

### Hashing

- Hash function converts search key into integer called hash code
- Hash code is compressed down into an index fitting in table size
- Allows O(1) access/addition/removal/retrieval

### Hash Code Contract

- If `a.equals(b)` is true, then `a.hashCode() == b.hashCode()` is true
- Converse isn't true --> indicates existence of a collision

### Collision Resolution

#### Open Addressing

##### Linear Probing

- Find next available index if hashing produces same index
- Paired with clustering
  - Mark each index EMPTY on table initialization
  - Once an element is added, change index to OCCUPIED
  - If removing an index, change status to AVAILABLE
  - If linear probing following collision, insert at next AVAILABLE
  - If retrieving a key inserted via linear probing, search until next EMPTY before deciding not in table

##### Quadratic Probing

- If collision at index k, check index k + 1, k + 4, k + 9... instead of next index

#### Separate Chaining

- Build a LinkedList at that index with each value being a key-value pair itself

## Sets

- No duplicates allowed (like mathematical sets)
- Types:
  - TreeSet: data inputted comes out in natural ordering
  - HashSet: data comes out in arbitrary ordering
  - LinkedHashSet: data comes out in order of inputting

## Searching

### Linear Search

- O(n)
- Search element by element until found or you reach the end of the list

### Binary Search

- O(log(n))
- Only works for sorted data!
- Start with the middle element
  - If target = middle, you're done
  - If target > middle repeat on the right half (assuming data sorted ascendingly)
  - If target < middle repeat on the left half (assuming data sorted ascendingly)

## LinkedLists

### Comparison w/ Arrays

- Arrays
  - Stored contiguously in memory
  - O(1) access due to indexing
  - O(n) insertion at front/middle
- LinkedLists
  - Stored non-contiguously in memory
  - O(n) access since traversal required
  - O(1) insertion/deletion (after traversing to the node)

## Trees

### Vocabulary

- Root node: node w/o parent node
- Leaf node: node w/o child nodes
- Interior node: node w/ >= 1 child nodes
- Sibling nodes: nodes sharing the same parent
- Descendant nodes: all child nodes and their children
- Height: number of levels separating furthest node from root
  - Root has height of 1
  - Empty tree has height of 0
  - Tree has 2^height - 1 ttoal nodes

### Binary Search Tree

#### Invariant

- Each node has 0-2 children (no duplicate keys)
- Value < node falls in left subtree
- Value > node falls in right subtree

#### Traversals

- Preorder: VLR (visit node, recursively visit left, recursively visit right)
  - Any BST can be reconstructed from its preorder traversal
- Inorder: LVR (recursively visit left, visit node, recursively visit right)
  - Inorder traversal outputs data in order
- Postorder: LRV (recursively visit left, recursively visit right, visit node)

### Node Deletion

- Three cases
  - Node to delete has 0 children: make its parent point to null
  - Node to delete has 1 child: make its parent point to child of node to delete
  - Node to delete has 2 children: make its parent point to:
    - Leftmost child in right subtree of node to delete
    - Rightmost child in left subtree of node to delete

#### Complexities

- O(log(n)) searching (can deterioriate to O(n) if BST --> LinkedList)
- O(n) for any traversal

#### Types

- Perfect BST: every node (except leaf nodes) has exactly 2 children
- Complete BST: perfect until last level, last level filled in left -> right order

## Heaps

### Invariant

- Complete binary trees (NOT BST's)
- Minheap: children are all greater than node (smallest node is tree root)
- Maxheap: children are all less than node (largest node is tree root)

### Operations

#### insert()

- Adds node to next available spot in complete binary tree
- Swaps child node with parent node while child node < parent node ("bubbling up")

#### getSmallest()

- Removes root node
- Places node furthest right in level h of tree in root's position
- Swaps parent node with child node while parent node > child node ("bubbling down")

### Implementation

- Use an array
  - getParent(i) = floor((i-1)/2)
  - getLeftChild(i) = 2i + 1
  - getRightChild(i) = 2i + 2

### Applications

#### Heapsort

- Add all available values to a minheap using insert()
- Remove all values until heap is empty using getSmallest()
- O(nlog(n))

#### Priority Queue

- Enqueue: use insert() O(log(n))
- Dequeue: use getSmallest() O(log(n))

### Comparison w/ BST

- Heap is always balanced bounding insertion/deletion with O(log(n))
- BST can degenerate into a LinkedList which gives O(n) insertion/deletion

## Graphs

### Vocabulary

- Adjacent/neighbor/successors: nodes having an edge from current node to that node
- Edge: connection between two nodes
- Undirected graph: all edges are two-way
- Directed graph: all edges are one-way
- Connected graph: each node has an edge to every other node
- Unconnected graph: not all nodes have an edge to every other node
- Weighted graph: edges have a cost associated with them
- Path: connection between nodes via edges
- Cycle: path starting and ending at same node
- Simple path: path that doesn't have any cycles in it
- Acyclic graph: graph that doesn't have any cycles in it

### Traversals

#### Breadth-First Traversal

- Visits all nodes at distance k first before visiting nodes at distance k + 1
- Implemented with queue
- Algorithm
  - Enqueue start node
  - Dequeue
  - If dequeued not in the set add it
  - For each neighbor of the added element, enqueue if it's not in the set
    - Enqueue in alphabetical order
  - Continue (2-4) until queue is empty

#### Depth-First Traversal

- Visits all nodes along a path before backtracking to most recent decision point
- Implemented with a stack
- Algorithm
  - Push start node onto stack
  - Pop
  - If popped not in set, add it
  - For each neighbor of popped, push it onto stack if it's not in the set
    - Push in alphabetical order
  - Continue (2-4) until the stack is empty

### Dijkstra's Algorithm

- Initialize start node's cost to 0
- Initialize remaining costs to infinity, remaining predecessors to none
- Find the node K that's not in the set S with the lowest cost C[K]
- Add the node K to the set S
- For each neighbor J of the node not in the set
  - Calculate the cost of reaching node J
    - Cost(J) = Cost(K) + Cost(K,J)
    - If Cost(J) < C[J], make replace C[J] and make predecessor K
- Repeat (3-5) until the set contains all of the nodes in the graph

## Sorting

### Vocabulary

- Stable: relative order of equal data is preserved
- In-place: uses only constant additional space
- Internal: all data being sorted fits in main memory
- External: all data being sorted doesn't fit in main memory
- Adaptive: algorithm runs faster the more the data is initially sorted

### Bubble Sort

- Iteratively sweep through, swapping left with right if left > right
- Average/worst case: O(n^2)
- Best case: O(n) (with boolean flag)
- In place and stable

### Selection Sort

- Sweep through array, find min value, make single swap at end
- Average/worst case: O(n^2)
- In place and stable

### Insertion Sort

- Pick value, move all values to left over until you've created an open slot, drop value in
- Average/worst case: O(n^2)
- In place and stable

### Quick Sort

- Hard split, easy join
- Pick a pivot, put all values less than pivot to left, all values greater than pivot to right
- Recursively do the quickSort on left and right until you have trivial sorting problem (one element)
- Average case: O(nlog(n))
- Worst case: O(n^2)
- In place and unstable

### Merge Sort

- Easy split, hard join
- Partition list into two lists, recursively sort two lists, join two lists
  - To join, keep finger on two lists
  - Move down each as placing in order
- Average/worst case: O(nlog(n))
- Not in place and stable

## Threading

### Initialization

- Extended the Thread class (not preferred)
- Implementing the Runnable interface

```java
  public class MessageTask extends Thread {

    @Override
    public void run() {
      System.out.println("running");
    }

  }

  Thread thread = new MessageTask();

  // MUST call start() instead of run()
  // run() executes code serially
  thread.start();
```

```java
  public class MessageTask implements Runnable {

    @Override
    public void run() {
      System.out.println("running");
    }

    Thread thread = new Thread(new MessageTask());

    thread.start();
  }
```

- `Runnable` is a functional interface so lambda expression can be used as well

```java
  Thread thread = new Thread(() -> {
    System.out.println("running");
  });

  thread.start();
```

### join()

- `join()` guarantees code afterward will run only after threads are finished

```java
  Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        System.out.println("thread one running");
      }
  });

  Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        System.out.println("thread two running");
      }
  });

  thread1.start();
  thread2.start();

  // thread1 and thread2 output is still intermixed (no order set for them)
  try {
    thread1.join();
    thread2.join();
  } catch (InterruptedException e) {
    // error handling
  }

  // Guaranteed to be printed after all printing by thread1, thread2
  System.out.println("Finished");
```

### sleep()

- Pauses thread execution for specified number of milliseconds

### Synchronization

- To avoid data races: concurrent writing to shared resource in heap memory
- Uses lock object: protects the critical section of memory

```java
  public class MessageTask implements Runnable {

    private static Object lock = new Object();
    private static Account account;

    public void run() {
      synchronized(lock) {
        // Some code modifying the account
      }
    }
  }
```

- Potential problems
  - Different locks for each thread (have static lock object shared by all threads)
  - Have atomic transactions (execute one write all within same synchronized block)
  - Avoid deadlock (prevent code depending on two lock objects)

## Software Life Cycle

- PPSCTDM
  - Problem specification
  - Program design
  - Selection of algorithms/data structures
  - Coding and debugging
  - Testing and verification
  - Documentation and support
  - Maintenance

### Software Development Processes

#### Waterfall

- Perform each step of software development cycle in order
  - Only start next step after completion of previous step
  - Emphasizes predictability but unworkable for large projects

#### Iterative

- Iteratively add incremental developments
  - Takes advantage of what was learned from previous system
  - Use working prototypes to refine specification
  - Emphasizes adaptibility
  - Ex: unified model, agile development (extreme programming XP)

##### Unified

- IECT: inception, elaboration, construction, transition
- Each stage treated as mini-waterfalls

##### Agile

- Short sprints of 1-4 weeks w/ focus on working software

### Problem Specification

- Create clear, accurate, unambiguous statement of problem you're trying to solve

### Program Design

- Break software into integrated set of components that work together to solve problem specification

### Program Testing

- Program verification (formal proofs of correctness)
- Empirical testing (unit tests, integration tests, alpha/beta tests)

# THE END