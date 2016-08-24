package com.dewafer.rpncalculator.core.token;

public interface Operator<V> extends OperatorToken, Comparable<OperatorToken> {

    int getRequiredOperandNumber();

    Operand<V> calculate(Operand<V>... operands);
}
