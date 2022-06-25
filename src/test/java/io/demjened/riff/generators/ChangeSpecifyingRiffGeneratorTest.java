package io.demjened.riff.generators;

import io.demjened.riff.model.ChangeType;
import io.demjened.riff.model.RiffData;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeSpecifyingRiffGeneratorTest {

    @Test
    public void testGenerate() {
        RiffData<String> riffData = new ChangeSpecifyingRiffGenerator<String>()
                .added("Ed")
                .added("Frank")
                .removed("Charlie")
                .removed("Diana")
                .modified("Bob")
                .unmodified("Alice")
                .generate();

        assertEquals(Set.of("Alice", "Bob", "Charlie", "Diana"), riffData.getLeft());
        assertEquals(Set.of("Ed", "Frank", "Bob", "Alice"), riffData.getRight());
        assertEquals(Map.of(
                ChangeType.ADDED, Set.of("Ed", "Frank"),
                ChangeType.REMOVED, Set.of("Charlie", "Diana"),
                ChangeType.UNMODIFIED, Set.of("Alice"),
                ChangeType.MODIFIED, Set.of("Bob")), riffData.getChanges());
    }

}
