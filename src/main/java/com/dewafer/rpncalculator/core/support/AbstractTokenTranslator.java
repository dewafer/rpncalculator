package com.dewafer.rpncalculator.core.support;

import com.dewafer.rpncalculator.core.TokenTranslatorProcessor;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.core.token.LeftParenthesis;
import com.dewafer.rpncalculator.core.token.RightParenthesis;
import com.dewafer.rpncalculator.core.token.Token;

/**
 * Token翻译器，负责将指定类型T翻译成Token。子类必须实现translate、isLeftParenthesis、isRightParenthesis方法。
 *
 * @param <T> 传入指定类型T
 */
public abstract class AbstractTokenTranslator<T, R> extends SingleStepProcessor<T, Token<R>> implements TokenTranslatorProcessor<T, R> {


    @Override
    protected final Token<R> process(T t) {
        if (isLeftParenthesis(t)) {
            return getLeftParenthesis();
        }

        if (isRightParenthesis(t)) {
            return getRightParenthesis();
        }

        Token<R> translated = translate(t);

        if (translated != null) {
            return translated;
        }

        throw new UnsupportedTokenException();
    }

    protected abstract Token<R> translate(T t);

    protected abstract boolean isLeftParenthesis(T t);

    protected abstract boolean isRightParenthesis(T t);

    @SuppressWarnings("unchecked")
    protected Token<R> getLeftParenthesis() {
        return LeftParenthesis.getInstance();
    }

    @SuppressWarnings("unchecked")
    protected Token<R> getRightParenthesis() {
        return RightParenthesis.getInstance();
    }

}
