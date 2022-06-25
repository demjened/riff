package io.demjened.riff.generators;

import io.demjened.riff.model.RiffData;

/**
 * Riff generator.<br/>
 * Implementing classes should follow the builder pattern where the last method call in the chain is
 * {@link #generate()} to generate the riff.
 *
 * @param <T> Type of items to diff
 */
public interface RiffGenerator<T> {

    /**
     * Generates the riff data.
     *
     * @return Generated riff data
     */
    RiffData<T> generate();

}
