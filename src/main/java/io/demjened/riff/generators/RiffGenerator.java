package io.demjened.riff.generators;

import io.demjened.riff.model.RiffData;

public interface RiffGenerator<T> {

    RiffData<T> generate();

}
