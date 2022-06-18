package io.demjened.riff.generators;

import io.demjened.riff.model.RiffData;

/**
 * Riff generator.
 *
 * @param <T> Type of items to diff
 */
public interface RiffGenerator<T> {

    RiffData<T> generate();

}
