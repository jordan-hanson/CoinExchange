package com.example.coinexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

@SpringBootApplication
public class CoinExchange {
    public static Scanner scanner = new Scanner(System.in);
    public static TreeMap<Integer, Integer> cashDrawer = new TreeMap<>();
    public static int total = 0;

    public static void continueTransaction(){
        System.out.println("Do you want to make another request? Choose yes or no.");
        String yesOrNo = scanner.next();
        switch(yesOrNo.toLowerCase()) {
            case "yes" :
                transactionInput();
                break;
            case "no" :
                System.out.println("Thanks for visiting, your transaction is complete");
                break;
            default:
                break;
        }
    }

    public static void deposit(){
        System.out.println("How much money (in dollars) do you want to deposit today?");
        int depositAmount = scanner.nextInt();
        int tempBalance = 0;

        if(depositAmount > 0){
            System.out.println("Okay, I can deposit up to " + depositAmount + " dollars for you.");
            System.out.println("For each bill type please specify how many you have to deposit");
            for(Integer i : cashDrawer.keySet()) {
                System.out.println("Please input how many " + i + "'s");
                int billAmount = scanner.nextInt();
                tempBalance = (depositAmount - (i * billAmount));
                int currentNumber = cashDrawer.get(i);
                cashDrawer.put(i, currentNumber + billAmount);
                depositAmount = depositAmount - (i * billAmount);
            }
            displayBalanceOfEach();
            displayTotalInAccount();
            System.out.println("Do you want change back from your account deposit?");
            String answer = scanner.next();
            if(answer.equals("yes")){
                withdrawal();
            } else {
            continueTransaction();}}
        else {
            System.out.println("You chose to deposit 0 dollars. Let's Try again.");
            transactionInput();
        }
    }
    public static void withdrawal(){
        System.out.println("How much money (in dollars) do you want to withdraw today?");
        int withdrawAmount = scanner.nextInt();
        int tempBalance = 0;
        System.out.println(total);
        if(total > withdrawAmount) {
            System.out.println("Okay, I can withdraw " + withdrawAmount + " dollars for you.");
                System.out.println("For each bill type please specify how many you want to with draw, but no more than " + withdrawAmount + " dollars.");
                for (Integer i : cashDrawer.keySet()) {
                    System.out.println("Please input how many " + i + "'s");
                    int billAmount = scanner.nextInt();
                    tempBalance = (withdrawAmount - (i * billAmount));
                    int currentNumber = cashDrawer.get(i);
                    cashDrawer.put(i, currentNumber - billAmount);
                    withdrawAmount = withdrawAmount - (i * billAmount);
                }
                displayBalanceOfEach();
                displayTotalInAccount();
                continueTransaction();
            } else {
                System.out.println("Your current balance is " + total + " dollars. Please try again within that amount.");
                transactionInput();

        }
    }

    public static void displayBalanceOfEach(){
        for (Map.Entry<Integer, Integer> entry :
                cashDrawer.entrySet())
            System.out.println(entry.getValue() + " of " + entry.getKey() + "'s");
    }

    public static void displayTotalInAccount() {
        total = 0;
        for (Map.Entry<Integer, Integer> entry :
                cashDrawer.entrySet())
            total = total + (entry.getValue() * entry.getKey());
        System.out.println("Your current balance is " + total + " dollars.");

    }

    public static void transactionInput(){
        System.out.println("Do you want to deposit, withdrawal, or see balance?");
        String transactionType = scanner.next();

        switch(transactionType.toLowerCase()) {
            case "deposit" :
                deposit();
                break;
            case "withdrawal" :
                withdrawal();
                break;
            case "balance" :
                displayBalanceOfEach();
                displayTotalInAccount();
                continueTransaction();
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CoinExchange.class, args);
        System.out.println("Hello, Welcome to Bank of Utah!");
        cashDrawer.put(1, 0);
        cashDrawer.put(5, 0);
        cashDrawer.put(10, 0);
        cashDrawer.put(20, 0);
        transactionInput();

    }

}
