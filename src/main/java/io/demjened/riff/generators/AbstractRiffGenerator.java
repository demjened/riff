package io.demjened.riff.generators;

import io.demjened.riff.model.RiffData;

import java.util.Collection;

public abstract class AbstractRiffGenerator<T> implements RiffGenerator<T> {

    protected final RiffData<T> data;

    public AbstractRiffGenerator() {
        this.data = new RiffData<>();
    }

    @Override
    public RiffData<T> generate() {
        return data;
    }

    protected AbstractRiffGenerator<T> setLeft(Collection<T> left) {
        data.getLeft().clear();
        data.getLeft().addAll(left);

        return this;
    }

    protected AbstractRiffGenerator<T> setRight(Collection<T> right) {
        data.getRight().clear();
        data.getRight().addAll(right);

        return this;
    }

}