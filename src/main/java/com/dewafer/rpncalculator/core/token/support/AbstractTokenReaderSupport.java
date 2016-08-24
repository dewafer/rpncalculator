package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.TokenReader;

import java.util.Iterator;

public abstract class AbstractTokenReaderSupport implements TokenReader {

    protected abstract boolean hasNext();

    protected abstract Token next();

    @Override
    public Iterator<Token> iterator() {
        return new Iterator<Token>() {
            @Override
            public boolean hasNext() {
                return AbstractTokenReaderSupport.this.hasNext();
            }

            @Override
            public Token next() {
                return AbstractTokenReaderSupport.this.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
