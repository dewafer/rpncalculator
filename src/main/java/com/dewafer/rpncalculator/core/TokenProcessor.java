package com.dewafer.rpncalculator.core;

import com.dewafer.rpncalculator.core.token.Token;

public interface TokenProcessor<R> extends Processor<Token, R> {

    @Override
    TokenProcessor push(Token token);
}
