package com.dewafer.rpncalculator.extend.token.impl.lazy;

import com.dewafer.rpncalculator.core.token.Operand;

public class LazyLoadOperand<V> implements Operand<V> {

    private String name;
    private OperandValueLoader<V> loader;

    public LazyLoadOperand(String name, OperandValueLoader<V> loader) {
        this.name = name;
        this.loader = loader;
    }

    @Override
    public V getValue() {
        return loader.loadValue(name);
    }
}

