package com.dewafer.rpncalculator.core.impl;

import com.dewafer.rpncalculator.core.Processor;
import com.dewafer.rpncalculator.core.TokenProcessor;
import com.dewafer.rpncalculator.core.TokenReaderProcessor;
import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.TokenReader;

public class TokenReaderProcessorImpl<R> implements TokenReaderProcessor<R> {

    private TokenProcessor<R> nextProcessor;

    public TokenReaderProcessorImpl(TokenProcessor<R> nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public Processor<TokenReader, R> push(TokenReader tokens) {
        for (Token token : tokens) {
            nextProcessor.push(token);
        }
        return this;
    }

    public R done() {
        return nextProcessor.done();
    }
}
