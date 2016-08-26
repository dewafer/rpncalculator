package com.dewafer.rpncalculator.core.token.support;

public abstract class AbstractNamedOperator<V> extends AbstractWeightedOperator<V> {

    private String name;

    public AbstractNamedOperator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
