package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.TokenTranslatorProcessor;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.core.token.LeftParenthesis;
import com.dewafer.rpncalculator.core.token.RightParenthesis;
import com.dewafer.rpncalculator.core.token.Token;

public abstract class AbstractTokenTranslator<T> extends SingleStepProcessor<T, Token> implements TokenTranslatorProcessor<T> {


    @Override
    protected Token process(T t) {
        if (isLeftParenthesis(t)) {
            return getLeftParenthesis();
        }

        if (isRightParenthesis(t)) {
            return getRightParenthesis();
        }

        Token translated = translate(t);

        if (translated != null) {
            return translated;
        }

        throw new UnsupportedTokenException();
    }

    protected abstract Token translate(T t);

    protected abstract boolean isLeftParenthesis(T t);

    protected abstract boolean isRightParenthesis(T t);

    protected Token getLeftParenthesis() {
        return LeftParenthesis.INSTANCE;
    }

    protected Token getRightParenthesis() {
        return RightParenthesis.INSTANCE;
    }

}
