package com.dewafer.rpncalculator.extend.token.impl.logical;

import com.dewafer.rpncalculator.core.token.support.AbstractNamedOperator;
import com.dewafer.rpncalculator.core.token.support.Associativity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BooleanLogicalOperator extends AbstractNamedOperator<Boolean> {

    private static final Map<String, String> SYMBOL_NAME_MAP;

    private static final Map<String, Integer> OPERATOR_WEIGHT_MAP;

    private static final String NAME_NOT = "not";

    private static final String NAME_AND = "and";

    private static final String NAME_OR = "or";

    private static final String NAME_IF_THEN = "if-then";

    private static final String NAME_IF_AND_ONLY_IF = "if-and-only-if";

    static {
        Map<String, String> symbolNameMap = new HashMap<String, String>();
        symbolNameMap.put("!", NAME_NOT);
        symbolNameMap.put("&&", NAME_AND);
        symbolNameMap.put("||", NAME_OR);
        symbolNameMap.put("->", NAME_IF_THEN);
        symbolNameMap.put("<->", NAME_IF_AND_ONLY_IF);
        SYMBOL_NAME_MAP = Collections.unmodifiableMap(symbolNameMap);

        Map<String, Integer> operatorWeightMap = new HashMap<String, Integer>();
        operatorWeightMap.put(NAME_NOT, 4);
        operatorWeightMap.put(NAME_AND, 3);
        operatorWeightMap.put(NAME_OR, 2);
        operatorWeightMap.put(NAME_IF_THEN, 1);
        operatorWeightMap.put(NAME_IF_AND_ONLY_IF, 0);
        OPERATOR_WEIGHT_MAP = Collections.unmodifiableMap(operatorWeightMap);
    }

    public static final Collection<String> SUPPORTED_SYMBOLS = SYMBOL_NAME_MAP.keySet();

    public BooleanLogicalOperator(String symbol) {
        super(SYMBOL_NAME_MAP.get(symbol));
    }

    @Override
    public int getRequiredOperandNumber() {
        return NAME_NOT.equals(getName()) ? 1 : 2;
    }

    @Override
    protected Boolean executeCalculate(Boolean... values) {
        if (NAME_NOT.equals(getName())) {
            return !values[0];
        }
        if (NAME_AND.equals(getName())) {
            return values[0] && values[1];
        }
        if (NAME_OR.equals(getName())) {
            return values[0] || values[1];
        }
        if (NAME_IF_THEN.equals(getName())) {
            // A -> B equals !A || B
            return !values[0] || values[1];
        }
        if (NAME_IF_AND_ONLY_IF.equals(getName())) {
            // <-> equals !XOR equals (A && B) || (!A && !B)
            return (values[0] && values[1])
                    || (!values[0] && !values[1]);
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public int getWeight() {
        return OPERATOR_WEIGHT_MAP.get(getName());
    }

    @Override
    public Associativity getAssociativity() {
        return NAME_NOT.equals(getName()) ? Associativity.RIGHT : Associativity.LEFT;
    }
}
