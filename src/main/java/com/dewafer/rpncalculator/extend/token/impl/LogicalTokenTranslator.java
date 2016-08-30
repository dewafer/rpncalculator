package com.dewafer.rpncalculator.extend.token.impl;

import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.extend.token.impl.logical.BooleanLogicalOperator;
import com.dewafer.rpncalculator.extend.token.impl.logical.BooleanOperand;
import com.dewafer.rpncalculator.extend.token.impl.support.AbstractStringTokenTranslator;

import java.util.regex.Pattern;

public class LogicalTokenTranslator extends AbstractStringTokenTranslator {
    @Override
    protected Token translate(String s) {

        if (BooleanLogicalOperator.SUPPORTED_SYMBOLS.contains(s)) {
            return new BooleanLogicalOperator(s);
        }

        if (Pattern.matches("^true|TRUE|T|t|false|FALSE|F|f$", s)) {
            return new BooleanOperand(Boolean.parseBoolean(s));
        }

        return null;
    }

}
