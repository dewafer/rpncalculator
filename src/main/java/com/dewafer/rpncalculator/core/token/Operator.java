package com.dewafer.rpncalculator.core.token;

public interface Operator extends OperatorToken, Comparable<OperatorToken> {

    int getRequiredOperandNumber();

    Operand calculate(Operand... operands);
}
