package io.ylab.storage.in.memory.data;

public interface Identifiable<I extends Comparable<? super I>> {
    I getId();

    void setId(I id);
}
