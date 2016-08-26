package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.Processor;

public abstract class SingleStepProcessor<T, R> implements Processor<T, R> {

    protected abstract R process(T t);

    private T current = null;

    @Override
    public Processor<T, R> push(T t) {
        if (current == null) {
            current = t;
        } else {
            throw new IllegalStateException("Already pushed.");
        }
        return this;
    }

    @Override
    public R done() {
        R result = process(current);
        current = null;
        return result;
    }
}
