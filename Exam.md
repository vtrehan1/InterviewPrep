# Object-Oriented Programming Clarifications

## Abstraction

### Procedural Abstraction

- Specify actions and hide algorithms
- E.g. sorting numbers in array
  - Is it bubble sort? Quick sort?

### Data Abstraction

- Specify data and hide representation
- E.g. storing index of names
  - ArrayList? LinkedList?

### Abstract Data Type

- Interfaces defining behavior

## Object-Oriented Structures

### Interface

#### General

- Specifies contract --> what it should do, not how it does it
- Provides abstract methods (usually no implementation included)
- Default methods provide default implementation (use `default` keyword)
- No `private` methods allowed
- ***Methods `public` even if not declared as such
  - So `void someMethod()` is inherently public

#### OOP Keys

- **Cannot be instantiated**
- Unidirectional IS-A relationship
- Keyword public not necessary (public by default)

#### Gotchas***

- Only methods in interface can be called on interface type reference
  - Must cast to call methods defined on class implementing interface
- Multiple interfaces can be implemented
- Interfaces can extend multiple interfaces
  - Implementing class has to implement ***all*** methods
- Interfaces can have `public static final` constants

```java
  public interface Trainable extends Callable, Capable {
    public void sit();
  }
  //Class implementing Trainable must implement all methods in Trainable, Callable and Capable
```

#### Key Interfaces

##### Iterable

- Interface extended by `Collection<E>` interface
- Must commit to generic type when implementing
- Must implement `iterator()` method
- For-each loop works on all arrays and objects of classes implementing this interface

##### Iterator

- Object of this interface type returned by `iterator()`
- Must implement `next()` and `hasNext()`

##### Comparable

- Must specify generic type when you're implementing
- Returning < 0 means `other` greater than `this`
- Returning > 0 means `this` greater than `other`
- Return = 0 means `this` and `other` have same sorting order
- Consistency between `equals()` and `compareTo()` recommended (a.k.a last line is `return this.compareTo(other) == 0;` )

```java
  public class Dog implements Comparable<Dog> {
    private int height;
    public int compareTo(Dog other) {
      if (this.height == other.height) {
        return 0;
      }
      if (this.height > other.height) {
        //Sorting order in increasing order of height
        return 1;
      }
      return -1;
     }
  }
```

## Class

### Generics

- Let user commit to a ***reference*** type (e.g. E in `ArrayList<E>`)
- Wrapper classes for primitive types are valid generic reference types
- Avoids issue of typecasting crashing at runtime

### Enums

- Used where you know all possible values (set of fixed values)
- If you need constructors, make them `private`
  - You won't be creating the constants

## Inheritance

### General

- Deriving class from base class
- General (base) to more specific (derived)
- Unidirectional IS-A relationship
- Cannot assign sublass reference to superclass object w/o cast

### Syntactical

```java
  public Person(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public Person() {
    this("Unknown",0);
  }

  //this() MUST be very first line in Person()
```

### Super

- `super()` call must be first line in constructor
  - `this()` and super cannot be called at the same time
- Java calls empty argument constructor in base class if `super()` not called
  - If you define non-default base class constructor you must define a custom default constructor to not have to explicitly invoke super matching created non-default constructor header
- `super` used to call parent method if you're overriding it

```java
  //Example
  public toString() {
    return super.toString() + admitYear + GPA
  }
```

### Overriding

- Method in subclass must have same header as method in parent class
- `toString()` and `equals()` often overriden
  - Default `equals()` is same as `==`
- NOT equal to overloading - same method w/ different parameters
- Can override variables too but not recommended (have to use `super.name` then)
- ***Can increase visibility of methods you're overriding but not decrease it
- ***Return type when overriding can also be a subtype of return type of parent
  - Called ***covariant*** return type

### Methods

- All `public` methods inherited
- `private` methods not inherited
- Constructors are not inherited

### Private Fields

- All private data inherited
- But cannot access it directly

```java
  public void method1 { name = 'John'; } //Nope
  public void method2 { setName('John'); } //Yep
```

- Doesn't make sense if someone can just extend and access all private info

### Protected/Package Protected

- Class element that is `protected` visible to all derived classes and all classes in package
- Package protected elements (no modifier) visible to all classes in package
- These elements have **direct** access

### Hierarchy

- All classes inherit from `Object` directly or indirectly
- Parent refers to direct superclass
- No multiple inheritance
  - Implement interfaces instead

### Early/Late binding

- Early (static) binding (C++) checks static type at runtime and calls that version of method
- Late binding (Java) will check dynamic type at runtime and calls that version of method

```java
  Faculty a = new Faculty();
  Person b = a;
  System.out.println(b.toString());

  //Early binding --> calls Person version of toString()
  //Late binding --> calls Faculty version of toString()
```

## Composition

### General

- Contains field which is a reference to another object
- HAS-A relationship
- Prefer composition over inheritance

### Adapter Class

- Class w/ object reference property
- "Renames" methods to make more useful

```java
  public class NickName {

    private Name name;

    public NickName() {
      name = new Name();
    }

    public void setNickName(String nickName) {
      name.setName(nickName;)
    }
  }
```

## Random Gotchas

- To string called implicitly in println() statement
- Mutators mean set methods

## Inheritance Gotchas

```java
  class A { int x = 10; }
  class B extends A { int x = 15; }
  class C extends B {
    int x = 5;
    public void f() {
      System.out.println('Hello');
    }
    public void printX() {
      (A (this)).x; //prints 10
    }
  }
```

- If all 3 classes had different variables, could use `this.x`, `this.y`, etc...
- `super.f()` works to print version in B but can't access any `f()` in A
  - Sees that object is of type C at runtime
- `super` and `this` only work in instance methods, not in main since it is `static`

### Cast Safety

- `getClass()` returns runtime class (dynamic type) of object
- `instanceof` is operator
  - Takes care of `null` check since `null instanceof Object` returns false
  - Works with interfaces too
  - You still have to cast however
  - Can only call static type methods w/o casting

### Abstract Class

- Can have fields, methods (even some w/ implementation)
- Can have `abstract` methods (implemented by subclasses)
  - If `abstract` method exists, class must be abstract
  - Abstract class doesn't require `abstract` methods
  - Cannot be final
- CANNOT instantiate
- Can still declare reference of type of `abstract` class
- Can have `private abstract` methods too
- If `abstract` methods in parent class unimplemented, subclass must be `abstract`

```java
  //WRONG
  class A {
    abstract void hello();
  }

  //Will compile
  abstract class B {
    abstract void sayHi()
  }
```

### Final keyword

- Final classes cannot be extended
- Final methods cannot be overriden
  - Abstract methods in abstract classes cannot be final

### Multiple Inheritance

- Not allowed in `java`
- Fake it w/ one base class and n number of interfaces

### Overriding

- Cannot call derived class's overloaded version of base class method if base class reference assigned

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

## Exceptions

### General

- Exception: rare event outside normal code behavior
- Error: rare event outside of your control

### Exception Handling

- All inherit from `Throwable` class
- `Object`
  - `Throwable`
    - Exception
    - Error
- ***Even if method has a `return`, `finally` executes and then `return` happens
- Order `catch` blocks from specific --> general
- Can rethrow exception in `catch` block

### Checked vs. Unchecked

- `Runtime Exception` and `Error` are unchecked
- Code throwing unchecked exception can be written w/o `try-catch`
  - Serious errors not handled by typical program
  - Can't really do anything about error
    - All errors unchecked
- All checked exceptions require one of:
  - Declare exception w/ `throws` in method header
    - Program will crash if `main` throws and exception occurs
  - Handle exception w/ `try-catch`
    - Handle after it propagates
    - Handle where thrown
- Extending `Exception` makes you checked
- Extending `Runtime Exception` makes you unchecked

### Overriding

- When overriding superclass methods, cannot throw additional exceptions
  - Can throw exception which is subclass of exception thrown in superclass method

```java
  public class SuperClass {
    public void someMethod() throws Exception1 {}
  }
  public class SubClass {

    //Illegal!
    public void someMethod() throws Exception1, Exception2 {}

    //Allowed if Exception2 extends Exception1
    public void someMethod() throws Exception2 {}
  }
```

## Nested Types

### General

- Top-level classes: normal, non-nested classes
- **Only one top-level class per file allowed
- Nested types declared inside class (or method)
  - Normally used only in enclosing class

### Types

- **Inner** class
  - Class declared inside class and doesn't have keyword `static`
- **Local** class
  - Class declared inside method
- **Anonymous** class
  - Local class w/o a name
- **Static** class
  - Class declared `static` inside a class

### Why

- Nested classes break encapsulation
  - Nested class considered member, just like fields and methods
  - Direct access to all members of enclosing class (even if `private`)
- Only needed in small part of code

### Inner class

- Outer and inner have direct access to each other's fields
- Can have any of the 4 access modifiers
- Tied to an outer class instance (should not have `static` fields)
- Top-level classes can only be `public` or package-protected

```java
  public class MyOuterClass {
    private int x;
    private class MyInnerClass {
      private int y;
      void foo() { x = 1; } //accessing private field
    }
    void bar() {
      MyInnerClass ic = new MyInnerClass();
      ic.y = 2; //accessing private field
    }
  }

```

- Class implementing `iterator` interface implemented this way
  - `hasNext()` called and if `true` `next()` called
- `private` inner class can only be instantiated w/ instance of outer class

### Local Classes

- Classes instantiated inside blocks (e.g. inside methods)
- Only method inside which declaration happens can instantiate local class object
- Only package protected
  - Can only instantiate inside method so `private` doesn't make sense
- Have access to outer class member variables
- Have access to method's local `final` variables too
  - In Java 8, access to "effectively `final`" variables
    - Variables whose values aren't changed post-declaration
  - Bc object persists longer than stack frame (which contains local variables)
- Have access to method parameters too in Java 8

```java
  public Iterator<Player> iterator() {
    class TeamIterator implements Iterator<Player> {
      //some implementation
    }
    return new TeamIterator();
  }
```

### Quiz Two Material

- Up to local classes
- Inner classes, local classes
- Exception, inheritance etc...

### Anonymous class

- Local class without name
- Define and make instance in one shot
- No constructor allowed since class anonymous

```java
  public Iterator<Player> iterator() {
    return new Iterator<Player>() {
      private int pos = 0;
      public boolean hasNext() {
        return pos < size;
      }
      public Player next() {
        return list[pos++];
      }
      public void remove() {
        throw new UnsupportedOperationException();
      }
    }; //semicolon important
  }
  /* new [name-of-interface]() { body of class } */
```

```java
  public class Tetris {}

  public class Driver {
    public static void main(String[] args) {
      Tetris superTetris = new Tetris() {
        public void dropPiece() {}
      };
      //Anonymous class extends Tetris
      /* new [name-of-parent-class]() { body of class } */
    }
  }
```

- Same access permissions as local classes

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

- Multiple inner class instances tied to one outer class instance

### Static Classes

- Inner class w/ keyword `static`
- Use like a normal top-level class
- Can use all 4 access modifiers
- Can only access `static` variables in outer class
  - Regardless of modifier of `static` members
- ***NOT tied to instance of the outer class

```java
  public class Outer {
    public int x;
    static int y;

    public Outer() {
      x = 1;
      y = 2;
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

### Lambda Expression

- Concise approach to define anonymous class instance
- Only for **functional interface**
  - Interface has only one abstract method
    - Can have one or more default and/or static methods

```java
  Task anonymousVersion = new Task() {
    public int compute(int x) {
      return x + x;
    }
  };

  //** No need to have type of method parameters **//
  Task lambdaVersion = (int x) -> {
      return x + x;
  };

  //If body of the method is one-liner
  Task anotherLambdaVersion = (x) -> x + x;

  //If method takes single argument and body of the method is one-liner
  Task yetAnotherLambdaVersion = x -> x + x;

  //If method takes no arguments and body of the method is one-liner
  Another yetAnotherLambdaVersion = () -> 10;
```

- Can have inline lambda expressions like `method((x,y) -> x*y);`
  - Passing object implementing functional interface to  `method()`

### Exam 1

- Wednesday 1 p.m.
- Up until Lambda Expressions covered
- Week 1 - 5 covered (including readings)
- Write the code, code snippets, short answer... (longer version of quiz)

### Exam Review

- Be ready to define abstraction, encapsulation
- is-A relationship (base classes, interfaces)
- Review `compareTo()`, `next()`, `hasNext()` implementations
- When calling `super()`, goes all the way to constructor for `Object`...
- Know polymorphism definition and have an intuitive understanding of it
- Know reason for nested types (break encapsulation & localize code...help with abstraction)

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
      this.age = age;
      this.name = name;
      //block will be pasted here
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
