package com.dewafer.rpncalculator.core.token.support;

public class NamedValueOperandImpl<V> extends ValueOperandImpl<V> {

    private String name;

    public NamedValueOperandImpl(String name, V value) {
        super(value);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
