package com.dewafer.rpncalculator.core.token.impl;

import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.core.token.*;

import java.util.*;
import java.util.regex.Pattern;

public class StringMathematicalTokenTranslator {

    public Token translate(String s) {
        if ("(".equals(s)) {
            return LeftParenthesis.INSTANCE;
        }

        if (")".equals(s)) {
            return RightParenthesis.INSTANCE;
        }

        if (IntegerOperator.SUPPORTED_NAMES.contains(s)) {
            return new IntegerOperator(s);
        }

        if (Pattern.matches("^\\d+$", s)) {
            return new IntegerOperand(Integer.parseInt(s));
        }

        throw new UnsupportedTokenException();
    }

    public static class IntegerOperand implements Operand<Integer> {
        private Integer value;

        public IntegerOperand(Integer value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static class IntegerOperator implements Operator<Integer> {

        static final Collection<String> SUPPORTED_NAMES = Collections.unmodifiableCollection(
                Arrays.asList("+", "-", "*", "/")
        );

        static final Map<String, Integer> OPERATOR_WEIGHT_MAP = new HashMap<String, Integer>();

        static {
            OPERATOR_WEIGHT_MAP.put("+", 0);
            OPERATOR_WEIGHT_MAP.put("-", 0);
            OPERATOR_WEIGHT_MAP.put("*", 1);
            OPERATOR_WEIGHT_MAP.put("/", 1);
        }

        private String name;

        public IntegerOperator(String name) {
            this.name = name;
        }

        @Override
        public int getRequiredOperandNumber() {
            return 2;
        }

        @Override
        public Operand<Integer> calculate(Operand<Integer>... operands) {
            if ("+".equals(name)) {
                return new IntegerOperand(operands[0].getValue() + operands[1].getValue());
            }
            if ("-".equals(name)) {
                return new IntegerOperand(operands[0].getValue() - operands[1].getValue());
            }
            if ("*".equals(name)) {
                return new IntegerOperand(operands[0].getValue() * operands[1].getValue());
            }
            if ("/".equals(name)) {
                return new IntegerOperand(operands[0].getValue() / operands[1].getValue());
            }
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(OperatorToken o) {
            if (!(o instanceof IntegerOperator)) {
                return 1;
            }
            IntegerOperator other = (IntegerOperator) o;
            return OPERATOR_WEIGHT_MAP.get(this.name) - OPERATOR_WEIGHT_MAP.get(other.name);
        }
    }
}
