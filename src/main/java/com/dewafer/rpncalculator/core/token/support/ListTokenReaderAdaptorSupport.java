package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.TokenReader;

import java.util.Iterator;
import java.util.List;

public abstract class ListTokenReaderAdaptorSupport<E> implements TokenReader {

    private List<E> inputList;

    public ListTokenReaderAdaptorSupport(List<E> inputList) {
        this.inputList = inputList;
    }

    protected abstract Token translate(E input);

    @Override
    public Iterator<Token> iterator() {
        final Iterator<E> iterator = inputList.iterator();
        return new Iterator<Token>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Token next() {
                return translate(iterator.next());
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
