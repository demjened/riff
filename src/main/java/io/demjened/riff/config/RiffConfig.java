package io.demjened.riff.config;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Configuration for riff generation.
 *
 * @param <T> Type of items to diff
 */
public class RiffConfig<T> {

    private BiFunction<T, T, Boolean> deepEqualityCheck;
    private Function<T, T> cloner;

    /**
     * Sets the deep equality check function. This function evaluates if two objects of type {@code T} are deep equal,
     * that is, if all of the relevant properties have the same value. Deep equal objects implicitly pass the equality
     * check.
     *
     * @param deepEqualityCheck The deep equality check function
     * @return The riff config
     */
    public RiffConfig<T> withDeepEqualityCheck(BiFunction<T, T, Boolean> deepEqualityCheck) {
        this.deepEqualityCheck = deepEqualityCheck;

        return this;
    }

    public RiffConfig<T> withCloner(Function<T, T> cloner) {
        this.cloner = cloner;

        return this;
    }

    public BiFunction<T, T, Boolean> getDeepEqualityCheck() {
        return deepEqualityCheck;
    }

    public Function<T, T> getCloner() {
        return cloner;
    }

}
