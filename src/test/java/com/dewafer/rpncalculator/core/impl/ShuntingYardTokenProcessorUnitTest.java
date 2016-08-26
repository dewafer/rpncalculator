package com.dewafer.rpncalculator.core.impl;

import com.dewafer.rpncalculator.core.TokenProcessor;
import com.dewafer.rpncalculator.core.exception.MismatchedParenthesesException;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.core.token.*;
import com.dewafer.rpncalculator.core.token.support.Associativity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ShuntingYardTokenProcessorUnitTest {

    @Mock
    TokenProcessor<?> nextProcessor;

    @InjectMocks
    @Spy
    ShuntingYardTokenProcessor<?> processor;

    @Mock
    Operand operand;

    @Mock
    Operand operandSecond;

    @Mock
    Operator operatorPlus;

    @Mock
    Operator operatorPlusSecond;

    @Mock
    Operator operatorMultiply;

    @Mock
    LeftParenthesis leftParenthesis;

    @Mock
    RightParenthesis rightParenthesis;

    @Mock
    Token unsupportedToken;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp(){
        // given
        given(operatorPlus.getAssociativity()).willReturn(Associativity.LEFT);
        given(operatorPlusSecond.getAssociativity()).willReturn(Associativity.LEFT);
        given(operatorMultiply.getAssociativity()).willReturn(Associativity.LEFT);
    }

    @Test
    public void testPush_pushOperand() {
        // inorder
        InOrder inOrder = inOrder(nextProcessor);

        // when
        processor.push(operand).done();

        // then
        then(processor).should().process(eq(operand));
        then(nextProcessor).should(inOrder).push(eq(operand));
        then(nextProcessor).should(inOrder).done();
    }

    @Test
    public void testPush_pushOperator() {
        // inorder
        InOrder inOrder = inOrder(nextProcessor);

        // when
        processor.push(operatorPlus).done();

        // then
        then(processor).should().process(eq(operatorPlus));
        then(nextProcessor).should(inOrder).push((eq(operatorPlus)));
        then(nextProcessor).should(inOrder).done();
    }

    @Test
    public void testPush_parentheses() {
        // inorder
        InOrder inOrder = inOrder(nextProcessor);

        // when
        processor.push(leftParenthesis)
                .push(operand)
                .push(rightParenthesis)
                .done();

        // then
        then(processor).should().process(eq(leftParenthesis));
        then(processor).should().process(eq(rightParenthesis));
        then(nextProcessor).should(inOrder).push(eq(operand));
        then(nextProcessor).should(inOrder).done();
    }

    @Test
    public void testPush_pushPlusAndPlus() {
        // inorder
        InOrder inOrder = inOrder(nextProcessor);

        // given
        given(operatorPlusSecond.compareTo(eq(operatorPlus))).willReturn(0);

        // when
        processor.push(operatorPlus)
                .push(operatorPlusSecond)
                .done();

        // then
        then(operatorPlusSecond).should().compareTo(eq(operatorPlus));
        then(nextProcessor).should(inOrder).push(operatorPlus);
        then(nextProcessor).should(inOrder).push(operatorPlusSecond);
    }

    @Test
    public void testPush_pushMultiplyAndPlus() {
        // inorder
        InOrder inOrder = inOrder(nextProcessor);

        // given
        given(operatorPlus.compareTo(eq(operatorMultiply))).willReturn(-1);

        // when
        processor.push(operatorMultiply)
                .push(operatorPlus)
                .done();

        // then
        then(operatorPlus).should().compareTo(eq(operatorMultiply));
        then(nextProcessor).should(inOrder).push(eq(operatorMultiply));
        then(nextProcessor).should(inOrder).push(eq(operatorPlus));
    }

    @Test
    public void testPush_pushPlusAndMultiply() {
        // inorder
        InOrder inOrder = inOrder(nextProcessor);

        // given
        given(operatorMultiply.compareTo(eq(operatorPlus))).willReturn(1);

        // when
        processor.push(operatorPlus)
                .push(operatorMultiply)
                .done();

        // then
        then(operatorMultiply).should().compareTo(eq(operatorPlus));
        then(nextProcessor).should(inOrder).push(eq(operatorMultiply));
        then(nextProcessor).should(inOrder).push(eq(operatorPlus));
    }

    @Test
    public void testPush_pushOperandPlusOperandSecond() {
        // inorder
        InOrder inOrder = inOrder(nextProcessor);

        // when
        processor.push(operand)
                .push(operatorPlus)
                .push(operandSecond)
                .done();

        // then
        then(nextProcessor).should(inOrder).push(eq(operand));
        then(nextProcessor).should(inOrder).push(eq(operandSecond));
        then(nextProcessor).should(inOrder).push(eq(operatorPlus));
        then(nextProcessor).should(inOrder).done();
    }

    @Test
    public void testPush_pushOperandPlusOperand() {
        // inorder
        InOrder inOrder = inOrder(nextProcessor);

        // when
        processor.push(operand)
                .push(operatorPlus)
                .push(operand)
                .done();

        // then
        then(nextProcessor).should(inOrder, times(2)).push(eq(operand));
        then(nextProcessor).should(inOrder).push(eq(operatorPlus));
        then(nextProcessor).should(inOrder).done();
    }

    @Test
    public void testPush_pushComplexExpression() {
        // this will push
        // operand * (( operand + operand ) * operand) + operand
        // and nextProcessor should get
        // operand operand operand + operand * * operand +

        // inorder
        InOrder inOrder = inOrder(nextProcessor);

        // when
        processor.push(operand)
                .push(operatorMultiply)
                .push(leftParenthesis)
                .push(leftParenthesis)
                .push(operand)
                .push(operatorPlus)
                .push(operand)
                .push(rightParenthesis)
                .push(operatorMultiply)
                .push(operand)
                .push(rightParenthesis)
                .push(operatorPlus)
                .push(operand)
                .done();

        // then
        then(nextProcessor).should(inOrder, times(3)).push(eq(operand));
        then(nextProcessor).should(inOrder).push(eq(operatorPlus));
        then(nextProcessor).should(inOrder).push(eq(operand));
        then(nextProcessor).should(inOrder, times(2)).push(eq(operatorMultiply));
        then(nextProcessor).should(inOrder).push(eq(operand));
        then(nextProcessor).should(inOrder).push(eq(operatorPlus));
    }

    @Test
    public void testPush_pushUnsupportedToken() {
        // expect to throw unsupported token exception
        expectedException.expect(UnsupportedTokenException.class);

        // when
        processor.push(unsupportedToken).done();
    }


    @Test
    public void testPush_pushRightParenthesisAndParenthesisMismatched() {
        // expect to throw mismatched parentheses exception
        expectedException.expect(MismatchedParenthesesException.class);

        // when
        processor.push(rightParenthesis).done();
    }

    @Test
    public void testPush_pushComplexExpressionAndParenthesisMismatched() {
        // this will push
        // operand * (( operand + operand ) * operand + operand
        // and should throw mismatched parentheses exception
        expectedException.expect(MismatchedParenthesesException.class);

        // when
        processor.push(operand)
                .push(operatorMultiply)
                .push(leftParenthesis)
                .push(leftParenthesis)
                .push(operand)
                .push(operatorPlus)
                .push(operand)
                .push(rightParenthesis)
                .push(operatorMultiply)
                .push(operand)
                .push(operatorPlus)
                .push(operand)
                .done();
    }

    @Test
    public void testDone_withoutPush() {
        // when
        processor.done();

        // then
        then(nextProcessor).should(never()).push(any(Token.class));
        then(nextProcessor).should().done();
    }
}