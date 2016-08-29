package com.dewafer.rpncalculator.extend.token.impl.lazy;

public interface OperandValueLoader<V> {
    public V loadValue(String name);
}
