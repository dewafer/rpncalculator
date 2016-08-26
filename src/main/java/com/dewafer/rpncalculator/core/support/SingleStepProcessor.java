package com.dewafer.rpncalculator.core.support;

import com.dewafer.rpncalculator.core.Processor;

/**
 * 单步处理器，继承此类的处理器只能push一次，push完成后必须调用done方法，如多次推送将抛出IllegalStateException。
 * 子类必须在process方法中实现传入T类型的处理并返回R类型。处理方法process将在done方法中运行。
 *
 * @param <T> 传入类型
 * @param <R> 处理完成后返回类型
 */
public abstract class SingleStepProcessor<T, R> implements Processor<T, R> {

    protected abstract R process(T t);

    private T current = null;

    @Override
    public Processor<T, R> push(T t) {
        if (current == null) {
            current = t;
        } else {
            throw new IllegalStateException("Already pushed.");
        }
        return this;
    }

    @Override
    public R done() {
        R result = process(current);
        current = null;
        return result;
    }
}
