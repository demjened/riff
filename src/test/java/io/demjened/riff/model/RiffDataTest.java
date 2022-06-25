package io.demjened.riff.model;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RiffDataTest {

    @Test
    public void testConstructor() {
        RiffData<Object> subject = new RiffData<>();

        assertEquals(Map.of(
                ChangeType.ADDED, Set.of(),
                ChangeType.REMOVED, Set.of(),
                ChangeType.MODIFIED, Set.of(),
                ChangeType.UNMODIFIED, Set.of()), subject.getChanges());
    }

    @Test
    public void testToString() {
        RiffData<Object> subject = new RiffData<>();

        String subjectAsString = subject.toString();

        assertTrue(subjectAsString.contains("left="));
        assertTrue(subjectAsString.contains("right="));
        assertTrue(subjectAsString.contains("changes="));
    }

}
