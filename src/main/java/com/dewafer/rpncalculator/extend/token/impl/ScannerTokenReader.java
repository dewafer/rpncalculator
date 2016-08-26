package com.dewafer.rpncalculator.extend.token.impl;

import com.dewafer.rpncalculator.core.TokenTranslatorProcessor;
import com.dewafer.rpncalculator.core.token.Token;
import com.dewafer.rpncalculator.core.token.support.IteratorTokenReaderSupport;

import java.util.Scanner;

public class ScannerTokenReader extends IteratorTokenReaderSupport {

    private Scanner scanner;
    private TokenTranslatorProcessor<String> translator;

    public ScannerTokenReader(Scanner scanner, TokenTranslatorProcessor<String> translator) {
        this.scanner = scanner;
        this.translator = translator;
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
        return translator.push(s).done();
    }
}
