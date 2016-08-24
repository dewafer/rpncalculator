package com.dewafer.rpncalculator.core.token.impl;

import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.support.AbstractTokenReaderSupport;

import java.util.Scanner;

public class ScannerTokenReader extends AbstractTokenReaderSupport {

    private Scanner scanner;
    private StringMathematicalTokenTranslator translator = new StringMathematicalTokenTranslator();

    public ScannerTokenReader(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    protected boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    protected Token next() {
        return translate(scanner.next());
    }

    private Token translate(String s) {
        return translator.translate(s);
    }
}
