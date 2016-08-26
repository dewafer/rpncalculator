package com.dewafer.rpncalculator.core;

import com.dewafer.rpncalculator.core.token.TokenReader;

/**
 * TokenReader 处理器
 *
 * @param <R> 处理完成后返回类型
 * @see TokenReader
 */
public interface TokenReaderProcessor<R> extends Processor<TokenReader, R> {
}
