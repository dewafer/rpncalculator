package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.token.Operator;

public abstract class AbstractWeightedOperator<V> extends AbstractValueCalculateOperator<V> {

    public abstract int getWeight();

    @Override
    public int compareTo(Operator<V> o) {
        if (o instanceof AbstractWeightedOperator) {
            return this.getWeight() - ((AbstractWeightedOperator) o).getWeight();
        }

        return 0 - o.compareTo(this);
    }
}
