package com.dewafer.rpncalculator.core.impl;

import com.dewafer.rpncalculator.core.TokenProcessor;
import com.dewafer.rpncalculator.core.exception.IllegalRequiredOperandNumberException;
import com.dewafer.rpncalculator.core.exception.MissingOperandException;
import com.dewafer.rpncalculator.core.exception.TooManyOperandsException;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.core.token.Operand;
import com.dewafer.rpncalculator.core.token.Operator;
import com.dewafer.rpncalculator.core.token.Token;

import java.util.Deque;
import java.util.LinkedList;

public class ReversePolishNotationExpressionProcessor implements TokenProcessor<Operand> {

    private Deque<Operand> operandStack = new LinkedList<Operand>();

    @Override
    public ReversePolishNotationExpressionProcessor push(Token token) {
        if (token instanceof Operand) {
            process((Operand) token);
            return this;
        }

        if (token instanceof Operator) {
            process((Operator) token);
            return this;
        }

        throw new UnsupportedTokenException();
    }

    protected void process(Operand operand) {
        operandStack.push(operand);
    }

    protected void process(Operator operator) {

        if (operator.getRequiredOperandNumber() < 0) {
            throw new IllegalRequiredOperandNumberException();
        }

        if (operandStack.size() < operator.getRequiredOperandNumber()) {
            throw new MissingOperandException();
        }

        Deque<Operand> arguments = new LinkedList<Operand>();
        for (int i = 0; i < operator.getRequiredOperandNumber(); i++) {
            arguments.push(operandStack.pop());
        }

        Operand calculatedResult = operator.calculate(arguments.toArray(new Operand[operator.getRequiredOperandNumber()]));
        if (calculatedResult != null) {
            operandStack.push(calculatedResult);
        }
    }

    @Override
    public Operand done() {
        if (operandStack.size() > 1) {
            throw new TooManyOperandsException();
        }
        return operandStack.isEmpty() ? null : operandStack.pop();
    }

}
