package com.dewafer.rpncalculator.core.token;

public interface Operand<V> extends Token<V> {

    V getValue();
}
