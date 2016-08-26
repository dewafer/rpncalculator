package com.dewafer.rpncalculator.core.token.support;

import com.dewafer.rpncalculator.core.token.Operator;

public abstract class AbstractWeightedOperator<V> implements Operator<V> {

    public abstract int getWeight();

    @Override
    public int compareTo(Operator o) {
        if (o instanceof AbstractWeightedOperator) {
            return this.getWeight() - ((AbstractWeightedOperator) o).getWeight();
        }

        return 0 - o.compareTo(this);
    }
}
