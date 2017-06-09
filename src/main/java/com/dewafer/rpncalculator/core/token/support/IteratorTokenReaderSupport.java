package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.TokenReader;

import java.util.Iterator;

public abstract class IteratorTokenReaderSupport<R> implements TokenReader<R> {

    protected abstract boolean hasNext();

    protected abstract Token<R> next();

    @Override
    public Iterator<Token<R>> iterator() {
        return new Iterator<Token<R>>() {
            @Override
            public boolean hasNext() {
                return IteratorTokenReaderSupport.this.hasNext();
            }

            @Override
            public Token<R> next() {
                return IteratorTokenReaderSupport.this.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
