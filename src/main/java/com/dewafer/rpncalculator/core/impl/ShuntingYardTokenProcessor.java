package com.dewafer.rpncalculator.core.impl;

import com.dewafer.rpncalculator.core.Processor;
import com.dewafer.rpncalculator.core.exception.MismatchedParenthesesException;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.core.token.*;
import com.dewafer.rpncalculator.core.token.support.Associativity;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 本类实现了调度场算法。参考：https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 *
 * @param <R> 最终返回值的类型
 */
public class ShuntingYardTokenProcessor<T, R> implements Processor<Token<T>, R> {

    private Deque<Token<T>> operatorStack = new LinkedList<Token<T>>();
    // output
    private final Processor<Token<T>, R> nextProcessor;

    public ShuntingYardTokenProcessor(Processor<Token<T>, R> nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public ShuntingYardTokenProcessor<T, R> push(Token<T> token) {
        if (token instanceof Operand) {
            process((Operand<T>) token);
            return this;
        }

        if (token instanceof Operator) {
            process((Operator<T>) token);
            return this;
        }

        if (token instanceof LeftParenthesis) {
            process((LeftParenthesis) token);
            return this;
        }

        if (token instanceof RightParenthesis) {
            process((RightParenthesis) token);
            return this;
        }

        throw new UnsupportedTokenException();
    }

    protected void process(Operand<T> operand) {
        // add to output queue
        nextProcessor.push(operand);
    }

    protected void process(Operator<T> operator) {
        Token<T> inStack;
        do {
            inStack = operatorStack.peek();
            if (inStack != null && inStack instanceof Operator) {
                Operator<T> inStackOperator = (Operator<T>) inStack;
                if (hasLessPrecedenceAccordingToAssociativity(operator, inStackOperator)) {
                    // output
                    nextProcessor.push(operatorStack.pop());
                    continue;
                }
            }
            break;
        } while (true);

        operatorStack.push(operator);
    }

    private boolean hasLessPrecedenceAccordingToAssociativity(Operator<T> o1, Operator<T> o2) {
        return (o1.getAssociativity() == Associativity.LEFT && o1.compareTo(o2) <= 0)
                || (o1.getAssociativity() == Associativity.RIGHT && o1.compareTo(o2) < 0);
    }

    @SuppressWarnings("unchecked")
    protected void process(LeftParenthesis leftParenthesis) {
        operatorStack.push(leftParenthesis);
    }

    protected void process(RightParenthesis rightParenthesis) {
        Token inStack;
        do {
            inStack = operatorStack.peek();
            if (inStack == null) {
                throw new MismatchedParenthesesException();
            }
            if (inStack instanceof LeftParenthesis) {
                operatorStack.pop();
                break;
            } else {
                // output
                nextProcessor.push(operatorStack.pop());
            }
        } while (true);
    }

    @Override
    public R done() {
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() instanceof Parenthesis) {
                throw new MismatchedParenthesesException();
            }
            nextProcessor.push(operatorStack.pop());
        }
        return nextProcessor.done();
    }
}
