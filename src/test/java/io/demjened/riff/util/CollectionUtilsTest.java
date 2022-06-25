package io.demjened.riff.util;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CollectionUtilsTest {

    @Test
    public void testMapify() {
        Set<String> items = Set.of("Alice", "Bob", "Charlie");

        Map<String, String> result = CollectionUtils.mapify(items);

        items.forEach(item -> assertEquals(item, result.get(item)));
        assertNull(result.get("Diana"));
    }

}
