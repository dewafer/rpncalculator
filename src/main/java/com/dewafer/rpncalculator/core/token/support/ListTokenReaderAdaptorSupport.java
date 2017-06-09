package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.TokenReader;

import java.util.Iterator;
import java.util.List;

public abstract class ListTokenReaderAdaptorSupport<I, O> implements TokenReader<O> {

    private List<I> inputList;

    public ListTokenReaderAdaptorSupport(List<I> inputList) {
        this.inputList = inputList;
    }

    protected abstract Token<O> translate(I input);

    @Override
    public Iterator<Token<O>> iterator() {
        final Iterator<I> iterator = inputList.iterator();
        return new Iterator<Token<O>>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Token<O> next() {
                return translate(iterator.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
