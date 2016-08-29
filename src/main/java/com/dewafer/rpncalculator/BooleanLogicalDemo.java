package com.dewafer.rpncalculator;

import com.dewafer.rpncalculator.core.RPNCalculator;
import com.dewafer.rpncalculator.core.exception.UnsupportedTokenException;
import com.dewafer.rpncalculator.extend.token.impl.ScannerTokenReader;
import com.dewafer.rpncalculator.extend.token.impl.StringLogicalTokenTranslator;

import java.util.Scanner;

public class BooleanLogicalDemo {
    public static void main(String[] args) {
        System.out.println("======================================================");
        System.out.println("| Logical calculator, can use !, &&, ||, ->, <->     |");
        System.out.println("| Please place space between booleans and operators. |");
        System.out.println("| Example input: true && ! false =(press enter)      |");
        System.out.println("======================================================");

        RPNCalculator<Integer> processor = new RPNCalculator<Integer>();

        ScannerTokenReader tokenReader = new ScannerTokenReader(
                new Scanner(System.in), new StringLogicalTokenTranslator());

        try {
            processor.push(tokenReader);
        } catch (UnsupportedTokenException e) {
            System.out.println(processor.done());
        }

    }
}
