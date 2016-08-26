package com.dewafer.rpncalculator.extend.token.impl;

import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.support.AbstractTokenTranslator;
import com.dewafer.rpncalculator.extend.token.impl.logical.BooleanLogicalOperator;
import com.dewafer.rpncalculator.extend.token.impl.logical.BooleanOperand;

import java.util.regex.Pattern;

public class StringLogicalTokenTranslator extends AbstractTokenTranslator<String> {
    @Override
    protected Token translate(String s) {

        if (BooleanLogicalOperator.SUPPORTED_NAMES.contains(s)) {
            return new BooleanLogicalOperator(s);
        }

        if (Pattern.matches("^true|TRUE|T|t|false|FALSE|F|t$", s)) {
            return new BooleanOperand(Boolean.parseBoolean(s));
        }

        return null;
    }

    @Override
    protected boolean isLeftParenthesis(String s) {
        return "(".equals(s);
    }

    @Override
    protected boolean isRightParenthesis(String s) {
        return ")".equals(s);
    }
}
