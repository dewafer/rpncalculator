package com.dewafer.rpncalculator.extend.token.impl;

import com.dewafer.rpncalculator.core.TokenTranslatorProcessor;
import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.support.IteratorTokenReaderSupport;

import java.util.Scanner;

public class ScannerTokenReader<R> extends IteratorTokenReaderSupport<R> {

    private Scanner scanner;
    private TokenTranslatorProcessor<String, R> translator;

    public ScannerTokenReader(Scanner scanner, TokenTranslatorProcessor<String, R> translator) {
        this.scanner = scanner;
        this.translator = translator;
    }

    @Override
    protected boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    protected Token<R> next() {
        return translate(scanner.next());
    }

    private Token<R> translate(String s) {
        return translator.push(s).done();
    }
}
