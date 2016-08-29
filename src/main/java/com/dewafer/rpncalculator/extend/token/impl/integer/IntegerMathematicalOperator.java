package com.dewafer.rpncalculator.extend.token.impl.integer;

import com.dewafer.rpncalculator.core.token.support.AbstractValueCalculateOperator;
import com.dewafer.rpncalculator.core.token.support.Associativity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IntegerMathematicalOperator extends AbstractValueCalculateOperator<Integer> {

    private static final Map<String, String> SYMBOL_NAME_MAP;

    private static final Map<String, Integer> OPERATOR_WEIGHT_MAP;

    private static final String NAME_ADD = "add";

    private static final String NAME_MINUS = "minus";

    private static final String NAME_MULTIPLY = "multiply";

    private static final String NAME_DIVIDE = "divide";

    static {
        Map<String, String> symbolNameMap = new HashMap<String, String>();
        symbolNameMap.put("+", NAME_ADD);
        symbolNameMap.put("-", NAME_MINUS);
        symbolNameMap.put("*", NAME_MULTIPLY);
        symbolNameMap.put("/", NAME_DIVIDE);
        SYMBOL_NAME_MAP = Collections.unmodifiableMap(symbolNameMap);


        Map<String, Integer> operatorWeightMap = new HashMap<String, Integer>();
        operatorWeightMap.put(NAME_ADD, 0);
        operatorWeightMap.put(NAME_MINUS, 0);
        operatorWeightMap.put(NAME_MULTIPLY, 1);
        operatorWeightMap.put(NAME_DIVIDE, 1);
        OPERATOR_WEIGHT_MAP = Collections.unmodifiableMap(operatorWeightMap);
    }

    public static final Collection<String> SUPPORTED_SYMBOLS = SYMBOL_NAME_MAP.keySet();

    public IntegerMathematicalOperator(String symbol) {
        super(SYMBOL_NAME_MAP.get(symbol));
    }

    @Override
    public int getRequiredOperandNumber() {
        return 2;
    }

    @Override
    protected Integer executeCalculate(Integer... values) {
        if (NAME_ADD.equals(getName())) {
            return values[0] + values[1];
        }
        if (NAME_MINUS.equals(getName())) {
            return values[0] - values[1];
        }
        if (NAME_MULTIPLY.equals(getName())) {
            return values[0] * values[1];
        }
        if (NAME_DIVIDE.equals(getName())) {
            return values[0] / values[1];
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
