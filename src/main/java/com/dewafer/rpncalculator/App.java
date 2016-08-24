package com.dewafer.rpncalculator;

import com.dewafer.rpncalculator.core.TokenProcessor;
import com.dewafer.rpncalculator.core.impl.ReversePolishNotationExpressionProcessor;
import com.dewafer.rpncalculator.core.impl.ShuntingYardTokenProcessor;
import com.dewafer.rpncalculator.core.token.Operand;
import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.impl.ScannerTokenReader;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        TokenProcessor<Operand> processor = new ShuntingYardTokenProcessor<Operand>(new ReversePolishNotationExpressionProcessor());

        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner);

        ScannerTokenReader tokenReader = new ScannerTokenReader(scanner);
        try {
            for (Token token : tokenReader) {
                processor.push(token);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println(processor.done());
    }
}
