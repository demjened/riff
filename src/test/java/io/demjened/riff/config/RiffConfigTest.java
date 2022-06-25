package io.demjened.riff.config;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RiffConfigTest {

    @Test
    public void testWithDeepEqualityCheck() {
        BiFunction<String, String, Boolean> deepEqualityCheck = (o1, o2) -> true;

        RiffConfig<String> subject = new RiffConfig<String>()
                .withDeepEqualityCheck(deepEqualityCheck);

        assertEquals(deepEqualityCheck, subject.getDeepEqualityCheck());
        assertTrue(subject.hasDeepEqualityCheck());
    }

}