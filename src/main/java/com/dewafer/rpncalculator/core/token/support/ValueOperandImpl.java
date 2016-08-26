package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.token.Operand;

public class ValueOperandImpl<V> implements Operand<V> {
    private V value;

    public ValueOperandImpl(V value) {
        this.value = value;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
