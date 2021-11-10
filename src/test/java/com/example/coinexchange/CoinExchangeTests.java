package com.example.coinexchange;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoinExchangeTests {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;

    @BeforeAll
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    public String runContinueTransactionAndGetOutputForNo(String inputText) {
        ByteArrayInputStream in = new ByteArrayInputStream(inputText.getBytes());
        System.out.println(in);
        System.setIn(in);
        CoinExchange.continueTransaction("no");
        String output = outContent.toString();
        return output;
    }

    @Test
    public void containsNoThanksForVisiting() {
        String output = runContinueTransactionAndGetOutputForNo("no");
        assertEquals(true, output.contains("Thanks for visiting, your transaction is complete"));
    }

    @Test
    public void testContinueTransaction(){
    }

    @Test
    public void testDeposit(){

    }

    @Test
    public void testWithdrawal(){

    }

    @Test
    public void testDisplayBalanceOfEach() {

    }

    @Test
    public void testDisplayTotalInAccount() {

    }

    @Test
    public void testTransactionInput() {

    }

}
