package com.dewafer.rpncalculator.core.impl;

import com.dewafer.rpncalculator.core.exception.IllegalRequiredOperandNumberException;
import com.dewafer.rpncalculator.core.exception.MissingOperandException;
import com.dewafer.rpncalculator.core.exception.TooManyOperandsException;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.core.token.Operand;
import com.dewafer.rpncalculator.core.token.Operator;
import com.dewafer.rpncalculator.core.token.Token;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class ReversePolishNotationExpressionProcessorUnitTest {

    @InjectMocks
    @Spy
    ReversePolishNotationExpressionProcessor processor;

    @Mock
    Operand operand;

    @Mock
    Operand operandSecond;

    @Mock
    Operand resultOperand;

    @Mock
    Operator operatorPlus;

    @Mock
    Operator operatorMultiply;

    @Mock
    Token unsupportedToken;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testPush_pushOperand() {
        // when
        Operand result = processor.push(operand).done();

        // then
        then(processor).should().process(eq(operand));

        // and
        assertThat(result, is(operand));
    }

    @Test
    public void testPush_pushOperator() {
        // given
        given(operatorPlus.getRequiredOperandNumber()).willReturn(0);
        given(operatorPlus.calculate()).willReturn(resultOperand);

        // when
        Operand result = processor.push(operatorPlus).done();

        // then
        then(processor).should().process(eq(operatorPlus));
        then(operatorPlus).should().calculate();

        // and
        assertThat(result, is(resultOperand));
    }

    @Test
    public void testPush_pushOperatorResultIsNull() {
        // given
        given(operatorPlus.getRequiredOperandNumber()).willReturn(0);
        given(operatorPlus.calculate()).willReturn(null);

        // when
        Operand result = processor.push(operatorPlus).done();

        // then
        then(processor).should().process(eq(operatorPlus));
        then(operatorPlus).should().calculate();

        // and
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testPush_pushOperandAndOperator() {
        // given
        given(operatorPlus.getRequiredOperandNumber()).willReturn(1);
        given(operatorPlus.calculate(eq(operand))).willReturn(resultOperand);

        // when
        Operand result = processor.push(operand).push(operatorPlus).done();

        // then
        then(processor).should().process(eq(operatorPlus));
        then(operatorPlus).should().calculate(eq(operand));

        // and
        assertThat(result, is(resultOperand));
    }

    @Test
    public void testPush_pushOperandPlusOperandSecond() {
        // given
        given(operatorPlus.getRequiredOperandNumber()).willReturn(2);
        given(operatorPlus.calculate(eq(operand), eq(operandSecond))).willReturn(resultOperand);

        // when
        Operand result = processor.push(operand)
                .push(operandSecond).push(operatorPlus).done();

        // then
        then(operatorPlus).should().calculate(eq(operand), eq(operandSecond));

        // and
        assertThat(result, is(resultOperand));
    }

    @Test
    public void testPush_pushComplexExpression() {
        // this will push
        // ((operand ((operand operandSecond +) operand *) *) operand +)
        // expect to be resolved as:
        // 1. ((operand (resultOperand operand *) *) operand +)
        // 2. ((operand operandSecond *) operand +)
        // 3. (resultOperand operand +)
        // 4. operandSecond


        // inorder
        InOrder inOrder = inOrder(operatorPlus, operatorMultiply);

        // given
        given(operatorPlus.getRequiredOperandNumber()).willReturn(2);
        given(operatorMultiply.getRequiredOperandNumber()).willReturn(2);

        given(operatorPlus.calculate(eq(operand), eq(operandSecond))).willReturn(resultOperand);
        given(operatorPlus.calculate(eq(resultOperand), eq(operand))).willReturn(operandSecond);
        given(operatorMultiply.calculate(eq(resultOperand), eq(operand))).willReturn(operandSecond);
        given(operatorMultiply.calculate(eq(operand), eq(operandSecond))).willReturn(resultOperand);

        // when
        Operand actualResult = processor.push(operand)
                .push(operand)
                .push(operandSecond)
                .push(operatorPlus)
                .push(operand)
                .push(operatorMultiply)
                .push(operatorMultiply)
                .push(operand)
                .push(operatorPlus)
                .done();

        // then
        then(operatorPlus).should(inOrder).calculate(eq(operand), eq(operandSecond));
        then(operatorMultiply).should(inOrder).calculate(eq(resultOperand), eq(operand));
        then(operatorMultiply).should(inOrder).calculate(eq(operand), eq(operandSecond));
        then(operatorPlus).should(inOrder).calculate(eq(resultOperand), eq(operand));

        // and
        assertThat(actualResult, is(operandSecond));
    }

    @Test
    public void testPush_pushUnsupportedToken() {
        // expect
        expectedException.expect(UnsupportedTokenException.class);

        // when
        processor.push(unsupportedToken).done();
    }

    @Test
    public void testPush_illegalRequiredOperandNumber() {
        // expect
        expectedException.expect(IllegalRequiredOperandNumberException.class);

        // given
        given(operatorPlus.getRequiredOperandNumber()).willReturn(-1);

        // when
        processor.push(operatorPlus).done();
    }

    @Test
    public void testPush_missingOperand() {
        // expect
        expectedException.expect(MissingOperandException.class);

        // given
        given(operatorPlus.getRequiredOperandNumber()).willReturn(1);

        // when
        processor.push(operatorPlus).done();
    }

    @Test
    public void testDone_tooManyOperands() {
        // expect
        expectedException.expect(TooManyOperandsException.class);

        // when
        processor.push(operand).push(operand).done();
    }

    @Test
    public void testDone_directlyDone() {
        // when
        Operand actualResult = processor.done();

        // then
        assertThat(actualResult, is(nullValue()));
    }
}