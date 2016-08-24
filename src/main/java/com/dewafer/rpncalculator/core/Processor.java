package com.dewafer.rpncalculator.core;

public interface Processor<T, R> {

    Processor<T, R> push(T t);

    R done();

}
