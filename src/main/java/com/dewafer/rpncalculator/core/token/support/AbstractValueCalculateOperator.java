package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.token.Operand;
import com.dewafer.rpncalculator.core.token.Operator;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractValueCalculateOperator<V> implements Operator<V> {

    @SuppressWarnings("unchecked")
    @Override
    public final Operand<V> resolve(Operand<V>... operands) {

        // get actual class of V(Value)
        Class<V> actualTypeOfValue = getActualTypeOfValue();
        if (actualTypeOfValue == null) {
            throw new IllegalArgumentException("You've got to tell me the type of V(Value).");
        }

        V[] arguments = (V[]) Array.newInstance(actualTypeOfValue, operands.length);

        for (int i = 0; i < operands.length; i++) {
            arguments[i] = operands[i].getValue();
        }

        return new ValueOperandImpl<V>(executeCalculate(arguments));
    }

    protected abstract V executeCalculate(V... values);

    @SuppressWarnings("unchecked")
    private Class<V> getActualTypeOfValue() {
        return (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
