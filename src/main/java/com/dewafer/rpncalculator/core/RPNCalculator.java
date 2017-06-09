package com.dewafer.rpncalculator.core;

import com.dewafer.rpncalculator.core.impl.ReversePolishNotationExpressionProcessor;
import com.dewafer.rpncalculator.core.impl.ShuntingYardTokenProcessor;
import com.dewafer.rpncalculator.core.impl.TokenReaderProcessorImpl;
import com.dewafer.rpncalculator.core.token.Operand;
import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.TokenReader;

public class RPNCalculator<R> implements Processor<TokenReader<R>, R> {


    private Processor<TokenReader<R>, Operand<R>> readerProcessor;

    public RPNCalculator() {
        readerProcessor = new TokenReaderProcessorImpl<R, Operand<R>>(
                new ShuntingYardTokenProcessor<R, Operand<R>>(
                        new ReversePolishNotationExpressionProcessor<R>()
                )
        );
    }

    @Override
    public RPNCalculator<R> push(TokenReader<R> tokens) {
        readerProcessor.push(tokens);
        return this;
    }

    @Override
    public R done() {
        Operand<R> result = readerProcessor.done();
        return result == null ? null : result.getValue();
    }
}
