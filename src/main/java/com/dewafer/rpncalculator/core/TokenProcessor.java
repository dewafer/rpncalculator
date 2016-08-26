package com.dewafer.rpncalculator.core;

import com.dewafer.rpncalculator.core.token.Token;

/**
 * Token处理器
 *
 * @param <R> 处理完成后返回类型
 * @see Token
 */
public interface TokenProcessor<R> extends Processor<Token, R> {

}
