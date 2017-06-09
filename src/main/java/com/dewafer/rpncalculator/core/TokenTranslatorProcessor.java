package com.dewafer.rpncalculator.core;

import com.dewafer.rpncalculator.core.token.Token;

/**
 * Token翻译处理器，将T类型翻译为Token
 *
 * @param <T> 处理类型
 */
public interface TokenTranslatorProcessor<T, R> extends Processor<T, Token<R>> {
}
