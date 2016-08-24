package com.dewafer.rpncalculator.core.impl;

import com.dewafer.rpncalculator.core.TokenProcessor;
import com.dewafer.rpncalculator.core.exception.MismatchedParenthesesException;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.core.token.*;

import java.util.Deque;
import java.util.LinkedList;

public class ShuntingYardTokenProcessor<R> implements TokenProcessor<R> {

    private Deque<OperatorToken> operatorStack = new LinkedList<OperatorToken>();
    // output
    private final TokenProcessor<R> nextProcessor;

    public ShuntingYardTokenProcessor(TokenProcessor<R> nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public ShuntingYardTokenProcessor push(Token token) {
        if (token instanceof Operand) {
            process((Operand) token);
            return this;
        }

        if (token instanceof Operator) {
            process((Operator) token);
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

    protected void process(Operand operand) {
        // add to output queue
        nextProcessor.push(operand);
    }

    protected void process(Operator operator) {
        OperatorToken inStack;
        do {
            inStack = operatorStack.peek();
            if (inStack != null && (inStack instanceof Comparable && operator.compareTo(inStack) <= 0)) {
                // output
                nextProcessor.push(operatorStack.pop());
            } else {
                break;
            }
        } while (true);

        operatorStack.push(operator);
    }

    protected void process(LeftParenthesis leftParenthesis) {
        operatorStack.push(leftParenthesis);
    }

    protected void process(RightParenthesis rightParenthesis) {
        OperatorToken inStack;
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
