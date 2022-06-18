package io.demjened.riff.generators;

import io.demjened.riff.model.RiffData;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AbstractRiffGeneratorTest {

    static class TestRiffGenerator extends AbstractRiffGenerator<String> {}

    private final TestRiffGenerator subject = new TestRiffGenerator();

    private final Set<String> items = Set.of("Alice", "Bob", "Charlie");

    @Test
    void testGenerate() {
        RiffData<String> result = subject.generate();

        assertSame(subject.data, result);
    }

    @Test
    void testSetLeft() {
        AbstractRiffGenerator<String> result = subject.setLeft(items);

        assertSame(subject, result);
        assertEquals(items, subject.data.getLeft());
    }

    @Test
    void testSetRight() {
        AbstractRiffGenerator<String> result = subject.setRight(items);

        assertSame(subject, result);
        assertEquals(items, subject.data.getRight());
    }

}
