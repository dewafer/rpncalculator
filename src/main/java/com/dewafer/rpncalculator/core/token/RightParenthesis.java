package com.dewafer.rpncalculator.core.token;

public final class RightParenthesis<T> implements Parenthesis<T> {

    private RightParenthesis() {
    }

    private static RightParenthesis<?> INSTANCE;

    @SuppressWarnings("unchecked")
    public static <R> RightParenthesis<R> getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RightParenthesis<R>();
        }
        return (RightParenthesis<R>) INSTANCE;
    }

}
