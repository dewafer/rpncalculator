package com.dewafer.rpncalculator.extend.token.impl;

import com.dewafer.rpncalculator.core.TokenTranslatorProcessor;
import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.support.ListTokenReaderAdaptorSupport;

import java.util.List;

public class ListTokenReader<E> extends ListTokenReaderAdaptorSupport<E> {

    private TokenTranslatorProcessor<E> translatorProcessor;

    public ListTokenReader(List<E> inputList, TokenTranslatorProcessor<E> translatorProcessor) {
        super(inputList);
        this.translatorProcessor = translatorProcessor;
    }

    @Override
    protected Token translate(E input) {
        return translatorProcessor.push(input).done();
    }
}
