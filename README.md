# Riff - Rich difference generator

![Build](https://github.com/demjened/riff/actions/workflows/maven.yml/badge.svg)
![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)

## About

**Riff** is a rich difference generator between two Java collections of the same type. It works similarly to the Unix `diff` command, and its output is a map of qualified changes aka. _riff_ ("rich diff").

The generation process identifies changes between the two collections, _left_ and _right_ as such:

> _left_ + _riff_ = _right_

Canonically _left_ holds the "before" state and _right_ holds the "after" state.  

Once _riff_ is generated, it will contain the following qualified changes:
- `ADDED`: Elements in _right_ but not in _left_
- `REMOVED`: Elements in _left_ but not in _right_
- `MODIFIED`: Elements both in _left_ and _right_ with content changes (see [Deep equality check](#deep-equality-check))
- `UNMODIFIED`: Elements both in _left_ and _right_ without content changes. 

## Riff generator strategies

Generator classes extend the `AbstractRiffGenerator` base class, which exposes common protected methods for setting states. Implementing subclasses should override and expose only those methods they need to work with.

Generators follow the builder pattern, calling a chain of methods to set the required states, terminating with `generate()`. 

### Left-right comparing riff generator

`ComparingRiffGenerator` implements a standard generator that accepts _left_ and _right_, then compares them to generate the riff.

**Example usage**
```java
// Generate the riff
RiffData<MyClass> riff = new ComparingRiffGenerator<MyClass>()
        .withLeft(left)
        .withRight(right)
        .generate();

// Get the changes
Map<ChangeType, Set<MyClass>> changes = riff.getChanges();
```

### Change specifying riff generator

`ChangeSpecifyingRiffGenerator` implements a generator which accepts _left_ and an explicitly stated list of changes. This class infers _right_ by applying the changes to _left_.

**Example usage**
```java
// Generate the riff
RiffData<MyClass> riff = new ChangeSpecifyingRiffGenerator<MyClass>()
        .withLeft(left)
        .added(alice)
        .added(bob)
        .removed(charlie)
        .modified(diane)
        .generate();

// Get the changes
Map<ChangeType, Set<MyClass>> changes = riff.getChanges();

// Get the inferred right collection
Set<MyClass> right = riff.getRight();
```

### Custom riff generator

You can implement your own riff generator by extending `AbstractRiffGenerator`.

**Example usage**

```java
import io.demjened.riff.generators.AbstractRiffGenerator;
import io.demjened.riff.model.RiffData;

import java.util.Collection;

class MyRiffGenerator<T> extends AbstractRiffGenerator<T> {

    // Override those methods you want to use in a builder chain, and cast to your subclass
    @Override
    protected AbstractRiffGenerator<T> withLeft(Collection<T> left) {
        /* Custom logic */

        return (MyRiffGenerator) super.withLeft(left);
    }

    @Override
    public RiffData<T> generate() {
        // Implement generator logic that returns a populated RiffData
    }

}
```

## Configuration

The base generator class accepts an optional `RiffConfig` object, which can configure the behavior

### Deep equality check

Riff generators use the standard identity equality check (`equals()`) to check whether an item is in _left_ and/or _right_. The process cannot determine whether an item both in _left_ and _right_ is changing in content, that is, some non-identity properties are being modified. By default such items will be marked as `UNMODIFIED` in the riff.

In order to assess content changes, the client needs to implement a deep equality check bifunction of type `BiFunction<T, T, Boolean>` and pass it to the config object with `withDeepEqualityCheck()`. 

**Example usage**
```java
class Person {
    
    private Long id;
    private String name;
    private Integer age;
    private Food favoriteFood; // Does not count as "modified" even if it changes
    
    @Override
    public boolean equals() {
        // Assuming equals() and hashCode() are implemented on the id property
    } 

    // Deep equality on id, name and age
    public boolean deepEquals(Person that) {
        return Objects.equals(this.id, that.id)
                && Objects.equals(this.name, that.name)
                && Objects.equals(this.age, that.age);
    }
    
}

Set<Person> left = Set.of(new Person(1, "Alice", 25, Food.SUSHI), ...);
Set<Person> right = Set.of(new Person(1, "Alice", 38, Food.SUSHI), ...);

// Riff generation
RiffData<Person> riff = new ComparingRiffGenerator()
        .withConfig(new RiffConfig<Person>()
                .withDeepEqualityCheck(Person::deepEquals))
        .withLeft(left)
        .withRight(right)
        .generate();

Set<Person> modified = riff.getChanges().get(ChangeType.MODIFIED); // Will contain "Alice"
```

See the unit tests for more detailed examples.

### Item cloning

TBD
