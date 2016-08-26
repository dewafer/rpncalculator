package com.dewafer.rpncalculator.extend.token.impl.integer;

import com.dewafer.rpncalculator.core.token.Operand;
import com.dewafer.rpncalculator.core.token.support.AbstractNamedOperator;
import com.dewafer.rpncalculator.core.token.support.Associativity;

import java.util.*;

public class IntegerMathematicalOperator extends AbstractNamedOperator<Integer> {

    public static final Collection<String> SUPPORTED_NAMES = Collections.unmodifiableCollection(
            Arrays.asList("+", "-", "*", "/")
    );

    private static final Map<String, Integer> OPERATOR_WEIGHT_MAP;

    static {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("+", 0);
        map.put("-", 0);
        map.put("*", 1);
        map.put("/", 1);
        OPERATOR_WEIGHT_MAP = Collections.unmodifiableMap(map);
    }

    public IntegerMathematicalOperator(String name) {
        super(name);
    }

    @Override
    public int getRequiredOperandNumber() {
        return 2;
    }

    @Override
    public Operand<Integer> calculate(Operand<Integer>... operands) {
        if ("+".equals(getName())) {
            return new IntegerOperand(operands[0].getValue() + operands[1].getValue());
        }
        if ("-".equals(getName())) {
            return new IntegerOperand(operands[0].getValue() - operands[1].getValue());
        }
        if ("*".equals(getName())) {
            return new IntegerOperand(operands[0].getValue() * operands[1].getValue());
        }
        if ("/".equals(getName())) {
            return new IntegerOperand(operands[0].getValue() / operands[1].getValue());
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWeight() {
        return OPERATOR_WEIGHT_MAP.get(getName());
    }

    @Override
    public Associativity getAssociativity() {
        return Associativity.LEFT;
    }
}
