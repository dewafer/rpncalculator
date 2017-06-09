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

/**
 * 本类实现了逆波兰表达式算法。
 * 参考：https://en.wikipedia.org/wiki/Reverse_Polish_notation
 *
 * @param <V> 最终返回值的类型
 */
public class ReversePolishNotationExpressionProcessor<V> implements TokenProcessor<Operand<V>> {

    private Deque<Operand<V>> operandStack = new LinkedList<Operand<V>>();

    @SuppressWarnings("unchecked")
    @Override
    public ReversePolishNotationExpressionProcessor<V> push(Token token) {
        if (token instanceof Operand) {
            process((Operand<V>) token);
            return this;
        }

        if (token instanceof Operator) {
            process((Operator<V>) token);
            return this;
        }

        throw new UnsupportedTokenException();
    }

    protected void process(Operand<V> operand) {
        operandStack.push(operand);
    }

    protected void process(Operator<V> operator) {

        if (operator.getRequiredOperandNumber() < 0) {
            throw new IllegalRequiredOperandNumberException();
        }

        if (operandStack.size() < operator.getRequiredOperandNumber()) {
            throw new MissingOperandException();
        }

        Deque<Operand<V>> arguments = new LinkedList<Operand<V>>();
        for (int i = 0; i < operator.getRequiredOperandNumber(); i++) {
            arguments.push(operandStack.pop());
        }

        @SuppressWarnings("unchecked")
        Operand<V> calculatedResult = operator.calculate(arguments.toArray(new Operand[arguments.size()]));
        if (calculatedResult != null) {
            operandStack.push(calculatedResult);
        }
    }

    @Override
    public Operand<V> done() {
        if (operandStack.size() > 1) {
            throw new TooManyOperandsException();
        }
        return operandStack.isEmpty() ? null : operandStack.pop();
    }

}
