package com.dewafer.rpncalculator.extend.token.impl.logical;

import com.dewafer.rpncalculator.core.token.Operand;
import com.dewafer.rpncalculator.core.token.support.AbstractNamedOperator;
import com.dewafer.rpncalculator.core.token.support.Associativity;

import java.util.*;

public class BooleanLogicalOperator extends AbstractNamedOperator<Boolean> {

    public static final Collection<String> SUPPORTED_NAMES = Collections.unmodifiableCollection(
            Arrays.asList("!", "&&", "||", "->", "<->")
    );

    private static final Map<String, Integer> OPERATOR_WEIGHT_MAP;

    static {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("!", 4);
        map.put("&&", 3);
        map.put("||", 2);
        map.put("->", 1);
        map.put("<->", 0);
        OPERATOR_WEIGHT_MAP = Collections.unmodifiableMap(map);
    }

    public BooleanLogicalOperator(String name) {
        super(name);
    }

    @Override
    public int getRequiredOperandNumber() {
        return "!".equals(getName()) ? 1 : 2;
    }

    @Override
    public Operand<Boolean> calculate(Operand<Boolean>... operands) {
        if ("!".equals(getName())) {
            return new BooleanOperand(!operands[0].getValue());
        }
        if ("&&".equals(getName())) {
            return new BooleanOperand(operands[0].getValue() && operands[1].getValue());
        }
        if ("||".equals(getName())) {
            return new BooleanOperand(operands[0].getValue() || operands[1].getValue());
        }
        if ("->".equals(getName())) {
            // A -> B equals !A || B
            return new BooleanOperand(!operands[0].getValue() || operands[1].getValue());
        }
        if ("<->".equals(getName())) {
            // <-> equals !XOR equals (A && B) || (!A && !B)
            return new BooleanOperand((operands[0].getValue() && operands[1].getValue())
                    || (!operands[0].getValue() && !operands[1].getValue()));
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public int getWeight() {
        return OPERATOR_WEIGHT_MAP.get(getName());
    }

    @Override
    public Associativity getAssociativity() {
        return "!".equals(getName()) ? Associativity.RIGHT : Associativity.LEFT;
    }
}
