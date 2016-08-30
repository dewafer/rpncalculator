package com.dewafer.rpncalculator.extend.token.impl;

import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.extend.token.impl.integer.IntegerMathematicalOperator;
import com.dewafer.rpncalculator.extend.token.impl.integer.IntegerOperand;
import com.dewafer.rpncalculator.extend.token.impl.support.AbstractStringTokenTranslator;

import java.util.regex.Pattern;

public class MathematicalTokenTranslator extends AbstractStringTokenTranslator {

    @Override
    protected Token translate(String s) {

        if (IntegerMathematicalOperator.SUPPORTED_SYMBOLS.contains(s)) {
            return new IntegerMathematicalOperator(s);
        }

        if (Pattern.matches("^\\d+$", s)) {
            return new IntegerOperand(Integer.parseInt(s));
        }

        return null;
    }

}
