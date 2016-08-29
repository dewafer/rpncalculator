package com.dewafer.rpncalculator.core;

import com.dewafer.rpncalculator.core.impl.ReversePolishNotationExpressionProcessor;
import com.dewafer.rpncalculator.core.impl.ShuntingYardTokenProcessor;
import com.dewafer.rpncalculator.core.impl.TokenReaderProcessorImpl;
import com.dewafer.rpncalculator.core.token.Operand;
import com.dewafer.rpncalculator.core.token.TokenReader;

public class RPNCalculator<R> implements TokenReaderProcessor<R> {


    private TokenReaderProcessor<Operand<R>> readerProcessor;

    public RPNCalculator() {
        readerProcessor = new TokenReaderProcessorImpl<Operand<R>>(
                new ShuntingYardTokenProcessor<Operand<R>>(
                        new ReversePolishNotationExpressionProcessor<R>()
                )
        );
    }

    @Override
    public RPNCalculator<R> push(TokenReader tokens) {
        readerProcessor.push(tokens);
        return this;
    }

    @Override
    public R done() {
        Operand<R> result = readerProcessor.done();
        return result == null ? null : result.getValue();
    }
}
