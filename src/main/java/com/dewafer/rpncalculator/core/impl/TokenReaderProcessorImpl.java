package com.dewafer.rpncalculator.core.impl;

import com.dewafer.rpncalculator.core.Processor;
import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.TokenReader;

/**
 * 本处理器负责处理TokenReader，将TokenReader中所有的token导入nextProcessor。可多次push TokenReader。
 *
 * @param <R> 最终返回值的类型
 */
public class TokenReaderProcessorImpl<T, R> implements Processor<TokenReader<T>,R> {

    private Processor<Token<T>, R> nextProcessor;

    public TokenReaderProcessorImpl(Processor<Token<T>, R> nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public Processor<TokenReader<T>, R> push(TokenReader<T> tokens) {
        for (Token<T> token : tokens) {
            nextProcessor.push(token);
        }
        return this;
    }

    public R done() {
        return nextProcessor.done();
    }
}
