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

  tropicalFruitArray[0] = new Fruit(); // compilation error
  fruitArray[0] = new TropicalFruit(); // throws exception
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