package com.dewafer.rpncalculator.extend;


import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.text.BreakIterator;
import java.util.StringTokenizer;

public class TokenizerPocTest {

    private static final String TEST_STRING = "1+2=\n3*4=\ntrue && false =";

    @Ignore
    @Test
    public void testStringTokenizer() {
        StringTokenizer stringTokenizer = new StringTokenizer(TEST_STRING);
        while (stringTokenizer.hasMoreTokens()) {
            System.out.println(stringTokenizer.nextToken());
        }
    }

    @Ignore
    @Test
    public void testStreamTokenizer() throws IOException {
        StreamTokenizer streamTokenizer = new StreamTokenizer(new StringReader(TEST_STRING));

        // print the stream tokens
        boolean eof = false;
        do {

            int token = streamTokenizer.nextToken();
            switch (token) {
                case StreamTokenizer.TT_EOF:
                    System.out.println("End of File encountered.");
                    eof = true;
                    break;
                case StreamTokenizer.TT_EOL:
                    System.out.println("End of Line encountered.");
                    System.out.println("Line Number:" + streamTokenizer.lineno());
                    break;
                case StreamTokenizer.TT_WORD:
                    System.out.println("Word: " + streamTokenizer.sval);
                    break;
                case StreamTokenizer.TT_NUMBER:
                    System.out.println("Number: " + streamTokenizer.nval);
                    break;
                default:
                    System.out.println((char) token + " encountered.");
                    if (token == '!') {
                        eof = true;
                    }
            }
        } while (!eof);
    }

    @Test
    public void testBreakIterator() {
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(TEST_STRING);

        int start = boundary.first();
        for (int end = boundary.next();
             end != BreakIterator.DONE;
             start = end, end = boundary.next()) {
            System.out.println(TEST_STRING.substring(start,end));
        }
    }

}
