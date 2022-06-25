package io.demjened.riff.config;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class RiffConfigTest {

    @Test
    public void testWithDeepEqualityCheck() {
        BiFunction<String, String, Boolean> deepEqualityCheck = (o1, o2) -> true;

        RiffConfig<String> subject = new RiffConfig<String>()
                .withDeepEqualityCheck(deepEqualityCheck);

        assertEquals(deepEqualityCheck, subject.getDeepEqualityCheck());
    }

    @Test
    public void testWithCloner() {
        Function<String, String> cloner = String::new;

        RiffConfig<String> subject = new RiffConfig<String>()
                .withCloner(cloner);

        assertEquals(cloner, subject.getCloner());
    }

}
