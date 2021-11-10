package com.example.coinexchange;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.TreeMap;

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
        CoinExchange.continueTransaction(inputText);
        String output = outContent.toString();
        return output;
    }

    @Test
    public void containsNoThanksForVisiting() {
        String output = runContinueTransactionAndGetOutputForNo("no");
        assertEquals(true, output.contains("Thanks for visiting, your transaction is complete"));
    }

    public String displayTotalInAccount(TreeMap<Integer, Integer> treeMap) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(treeMap);

        ByteArrayInputStream in = new ByteArrayInputStream(byteOut.toByteArray());
        System.out.println(in);
        System.setIn(in);
        CoinExchange.displayTotalInAccount(treeMap);
        String output = outContent.toString();
        return output;
    }
    
    @Test
    public void testDisplayTotal() throws IOException {
        TreeMap<Integer, Integer> cashDrawer = new TreeMap<>();
        cashDrawer.put(1, 1);
        cashDrawer.put(5, 2);
        cashDrawer.put(10, 3);
        cashDrawer.put(20, 4);
        String output = displayTotalInAccount(cashDrawer);
        assertEquals(true, output.contains("Your current balance is 121 dollars."));
    }

    public String displayBalanceOfEach(TreeMap<Integer, Integer> treeMap) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(treeMap);

        ByteArrayInputStream in = new ByteArrayInputStream(byteOut.toByteArray());
        System.out.println(in);
        System.setIn(in);
        CoinExchange.displayBalanceOfEach(treeMap);
        String output = outContent.toString();
        return output;
    }

    @Test
    public void testDisplayBalanceOfEach() throws IOException {
        TreeMap<Integer, Integer> cashDrawer = new TreeMap<>();
        cashDrawer.put(1, 1);
        cashDrawer.put(5, 2);
        cashDrawer.put(10, 3);
        cashDrawer.put(20, 4);
        String output = displayBalanceOfEach(cashDrawer);
        assertEquals(true, output.contains("1 of 1's"));
        assertEquals(true, output.contains("2 of 5's"));
        assertEquals(true, output.contains("3 of 10's"));
        assertEquals(true, output.contains("4 of 20's"));
    }

}
