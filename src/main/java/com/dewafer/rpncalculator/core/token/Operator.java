package com.dewafer.rpncalculator.core.token;

import com.dewafer.rpncalculator.core.token.support.Associativity;

public interface Operator<V> extends Token<V>, Comparable<Operator<V>> {

    Associativity getAssociativity();

    int getRequiredOperandNumber();

    Operand<V> resolve(Operand<V>... operands);
}
