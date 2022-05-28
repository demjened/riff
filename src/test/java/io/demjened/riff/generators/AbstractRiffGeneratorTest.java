package io.demjened.riff.generators;

import io.demjened.riff.model.RiffData;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractRiffGeneratorTest {

    static class TestRiffGenerator extends AbstractRiffGenerator<String> {}

    private final TestRiffGenerator subject = new TestRiffGenerator();

    private final Set<String> items = Set.of("Alice", "Bob", "Charlie");

    @Test
    public void testGenerate() {
        RiffData<String> result = subject.generate();

        assertSame(subject.data, result);
    }

    @Test
    public void testSetLeft() {
        subject.setLeft(items);

        assertEquals(items, subject.data.getLeft());
    }

    @Test
    public void testSetRight() {
        subject.setRight(items);

        assertEquals(items, subject.data.getRight());
    }

}