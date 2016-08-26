package com.dewafer.rpncalculator.core.impl;

import com.dewafer.rpncalculator.core.TokenProcessor;
import com.dewafer.rpncalculator.core.exception.MismatchedParenthesesException;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.core.token.*;
import com.dewafer.rpncalculator.core.token.support.Associativity;

import java.util.Deque;
import java.util.LinkedList;

public class ShuntingYardTokenProcessor<R> implements TokenProcessor<R> {

    private Deque<Token> operatorStack = new LinkedList<Token>();
    // output
    private final TokenProcessor<R> nextProcessor;

    public ShuntingYardTokenProcessor(TokenProcessor<R> nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public ShuntingYardTokenProcessor<R> push(Token token) {
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

    protected void process(Operand<?> operand) {
        // add to output queue
        nextProcessor.push(operand);
    }

    protected void process(Operator<?> operator) {
        Token inStack;
        do {
            inStack = operatorStack.peek();
            if (inStack != null && inStack instanceof Operator) {
                Operator<?> inStackOperator = (Operator<?>) inStack;
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

    private boolean hasLessPrecedenceAccordingToAssociativity(Operator<?> o1, Operator<?> o2) {
        return (o1.getAssociativity() == Associativity.LEFT && o1.compareTo(o2) <= 0)
                || (o1.getAssociativity() == Associativity.RIGHT && o2.compareTo(o2) < 0);
    }

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
