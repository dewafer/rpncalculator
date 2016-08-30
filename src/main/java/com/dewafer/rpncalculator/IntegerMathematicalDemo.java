package com.dewafer.rpncalculator;

import com.dewafer.rpncalculator.core.RPNCalculator;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.extend.token.impl.ScannerTokenReader;
import com.dewafer.rpncalculator.extend.token.impl.MathematicalTokenTranslator;

import java.util.Scanner;

public class IntegerMathematicalDemo {
    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("| Integer calculator, can calculate +, -, *, /.      |");
        System.out.println("| Please place space between numbers and operators.  |");
        System.out.println("| Example input: 256 * 128 =(press enter)            |");
        System.out.println("======================================================");

        RPNCalculator<Integer> processor = new RPNCalculator<Integer>();

        ScannerTokenReader tokenReader = new ScannerTokenReader(
                new Scanner(System.in), new MathematicalTokenTranslator());

        try {
            processor.push(tokenReader);
        } catch (UnsupportedTokenException e) {
            System.out.println(processor.done());
        }

    }
}
