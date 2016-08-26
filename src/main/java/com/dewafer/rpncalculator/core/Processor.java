package com.dewafer.rpncalculator.core;

/**
 * 处理器接口
 *
 * @param <T> 传入类型
 * @param <R> 处理完成后返回类型
 */
public interface Processor<T, R> {

    Processor<T, R> push(T t);

    R done();

}
