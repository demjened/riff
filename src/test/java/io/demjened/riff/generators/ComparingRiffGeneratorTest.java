package io.demjened.riff.generators;

import io.demjened.riff.config.RiffConfig;
import io.demjened.riff.model.ChangeType;
import io.demjened.riff.model.RiffData;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComparingRiffGeneratorTest {

    static class Person {

        private final Integer id;
        private final String name;

        public Person(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public boolean deepEquals(Person that) {
            return Objects.equals(this.id, that.id)
                    && Objects.equals(this.name, that.name);
        }

        // Equality and hashcode are based on id
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return id.equals(person.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

    }

    @Test
    public void testGenerate() {
        Set<String> left = Set.of("Alice", "Bob", "Charlie", "Diana");
        Set<String> right = Set.of("Bob", "Alice", "Ed", "Frank");

        RiffData<String> riffData = new ComparingRiffGenerator<String>()
                .setLeft(left)
                .setRight(right)
                .generate();

        assertEquals(left, riffData.getLeft());
        assertEquals(right, riffData.getRight());
        assertEquals(Map.of(
                ChangeType.ADDED, Set.of("Ed", "Frank"),
                ChangeType.REMOVED, Set.of("Charlie", "Diana"),
                ChangeType.UNMODIFIED, Set.of("Alice", "Bob"),
                ChangeType.MODIFIED, Set.of()), riffData.getChanges());
    }

    @Test
    public void testGenerate_WithDeepEqualityCheck() {
        Person alice = new Person(1, "Alice");
        Person bob = new Person(2, "Bob");
        Person charlie = new Person(3, "Charlie");
        Person diana = new Person(4, "Diana");
        Person ed = new Person(5, "Ed");
        Person frank = new Person(6, "Frank");

        Set<Person> left = Set.of(alice, bob, charlie, diana);
        Set<Person> right = Set.of(new Person(2, "Robert"), alice, ed, frank);

        RiffData<Person> riffData = new ComparingRiffGenerator<Person>()
                .withConfig(new RiffConfig<Person>()
                        .withDeepEqualityCheck(Person::deepEquals))
                .setLeft(left)
                .setRight(right)
                .generate();

        assertEquals(Map.of(
                ChangeType.ADDED, Set.of(ed, frank),
                ChangeType.REMOVED, Set.of(charlie, diana),
                ChangeType.UNMODIFIED, Set.of(alice),
                ChangeType.MODIFIED, Set.of(bob)), riffData.getChanges());
    }

}
