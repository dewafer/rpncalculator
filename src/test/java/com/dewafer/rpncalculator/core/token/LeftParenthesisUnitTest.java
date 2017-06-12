package com.dewafer.rpncalculator.core.token;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static java.lang.System.identityHashCode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by dewafer on 2017/6/12.
 */
@RunWith(MockitoJUnitRunner.class)
public class LeftParenthesisUnitTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testMultiGenericLeftParenthesis() {
        LeftParenthesis<Integer> integerLeftParenthesis = LeftParenthesis.getInstance();
        LeftParenthesis<Boolean> booleanLeftParenthesis = LeftParenthesis.getInstance();
        LeftParenthesis<Void> voidLeftParenthesis = LeftParenthesis.getInstance();

        assertThat(identityHashCode(integerLeftParenthesis), is(identityHashCode(booleanLeftParenthesis)));
        assertThat(identityHashCode(booleanLeftParenthesis), is(identityHashCode(voidLeftParenthesis)));
    }
}