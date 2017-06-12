package com.dewafer.rpncalculator.core.token;

public final class LeftParenthesis<T> implements Parenthesis<T> {

    private LeftParenthesis() {
    }

    private static LeftParenthesis<?> INSTANCE;

    @SuppressWarnings("unchecked")
    public static <R> LeftParenthesis<R> getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LeftParenthesis<R>();
        }
        return (LeftParenthesis<R>) INSTANCE;
    }
}