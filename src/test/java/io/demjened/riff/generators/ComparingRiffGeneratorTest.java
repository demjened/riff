package io.demjened.riff.generators;

import io.demjened.riff.model.ChangeType;
import io.demjened.riff.model.RiffData;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ComparingRiffGeneratorTest {

    private final ComparingRiffGenerator<String> subject = new ComparingRiffGenerator<>();

    @Test
    void generate() {
        Set<String> left = Set.of("Alice", "Bob", "Charlie", "Diana");
        Set<String> right = Set.of("Bob", "Alice", "Ed", "Frank");

        RiffData<String> riffData = subject.setLeft(left)
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

}
