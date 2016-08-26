package com.dewafer.rpncalculator.extend.token.impl;

import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.support.AbstractTokenTranslator;
import com.dewafer.rpncalculator.extend.token.impl.integer.IntegerMathematicalOperator;
import com.dewafer.rpncalculator.extend.token.impl.integer.IntegerOperand;

import java.util.regex.Pattern;

public class StringMathematicalTokenTranslator extends AbstractTokenTranslator<String> {

    @Override
    protected Token translate(String s) {

        if (IntegerMathematicalOperator.SUPPORTED_NAMES.contains(s)) {
            return new IntegerMathematicalOperator(s);
        }

        if (Pattern.matches("^\\d+$", s)) {
            return new IntegerOperand(Integer.parseInt(s));
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
