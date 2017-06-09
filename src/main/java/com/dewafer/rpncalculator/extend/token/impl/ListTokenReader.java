package com.dewafer.rpncalculator.extend.token.impl;

import com.dewafer.rpncalculator.core.TokenTranslatorProcessor;
import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.support.ListTokenReaderAdaptorSupport;

import java.util.List;

public class ListTokenReader<I, O> extends ListTokenReaderAdaptorSupport<I, O> {

    private TokenTranslatorProcessor<I, O> translatorProcessor;

    public ListTokenReader(List<I> inputList, TokenTranslatorProcessor<I, O> translatorProcessor) {
        super(inputList);
        this.translatorProcessor = translatorProcessor;
    }

    @Override
    protected Token<O> translate(I input) {
        return translatorProcessor.push(input).done();
    }
}
