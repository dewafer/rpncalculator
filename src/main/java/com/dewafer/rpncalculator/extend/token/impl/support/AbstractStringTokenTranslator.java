package com.dewafer.rpncalculator.extend.token.impl.support;

import com.dewafer.rpncalculator.core.support.AbstractTokenTranslator;

public abstract class AbstractStringTokenTranslator<R> extends AbstractTokenTranslator<String, R> {

    public static final String STRING_LEFT_PARENTHESIS = "(";
    public static final String STRING_RIGHT_PARENTHESIS = ")";

    @Override
    protected boolean isLeftParenthesis(String s) {
        return STRING_LEFT_PARENTHESIS.equals(s);
    }

    @Override
    protected boolean isRightParenthesis(String s) {
        return STRING_RIGHT_PARENTHESIS.equals(s);
    }
}
