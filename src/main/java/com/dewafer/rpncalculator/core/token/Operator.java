package com.dewafer.rpncalculator.core.token;

import com.dewafer.rpncalculator.core.token.support.Associativity;

public interface Operator<V> extends Token, Comparable<Operator> {

    Associativity getAssociativity();

    int getRequiredOperandNumber();

    Operand<V> calculate(Operand<V>... operands);
}
