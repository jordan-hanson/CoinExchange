package com.example.coinexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

@SpringBootApplication
public class CoinExchange {
    public static Scanner scanner = new Scanner(System.in);
    public static TreeMap<Integer, Integer> cashDrawer = new TreeMap<>();
    public static int total = 0;

    public static void continueTranscation(){
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
        System.out.println("How much money do you want to deposit today?");
        int depositAmount = scanner.nextInt();
        int tempBalance = 0;
        if(depositAmount > 0){System.out.println("Okay, I can deposit " + depositAmount + " dollars for you.");}

        System.out.println("For each bill type please specify how many you have to deposit");
        for(Integer i : cashDrawer.keySet()) {
            System.out.println("Please input how many " + i + "'s");
            int billAmount = scanner.nextInt();
            tempBalance = (depositAmount - (i * billAmount));
            System.out.println(depositAmount);
            System.out.println(tempBalance);



            if(tempBalance > i){
                int currentNumber = cashDrawer.get(i);
                cashDrawer.put(i, currentNumber + billAmount);
                depositAmount = depositAmount - (i * billAmount);
                System.out.println(depositAmount);
            }
            if(tempBalance < i){
                System.out.println("Oops, it seems we don't have anymore bills to deposit. Please try depositing again");
                tempBalance = 0;
                break;
            }
            else { break;}



        }
        displayBalanceOfEach();
        displayTotalInAccount();
        continueTranscation();
    }
    public static void withdrawal(){
        System.out.println("How much money do you want to withdraw today?");
        int withdrawAmount = scanner.nextInt();
        if(total > withdrawAmount){
        System.out.println("Okay, I can withdraw " + withdrawAmount + " dollars for you.");

        for(Integer i : cashDrawer.keySet()) {
            System.out.println("Please input how many " + i + "'s you want");
            int billAmount = scanner.nextInt();
            System.out.println(billAmount);
            int currentNumber = cashDrawer.get(i);
            System.out.println(currentNumber);
            System.out.println(billAmount - currentNumber > 0);
            if((billAmount - currentNumber) > 0){
                cashDrawer.put(i, billAmount - currentNumber);
            }
            if((billAmount - currentNumber) < 1){
                System.out.println("Oops, it seems we don't have enough bills for that amount");
            }
        }
        } else {
            System.out.println("There are insufficient funds for this transaction.");
            displayTotalInAccount();
            continueTranscation();
        }
        displayBalanceOfEach();
        displayTotalInAccount();
        continueTranscation();
    }

    public static void displayBalanceOfEach(){
        for (Map.Entry<Integer, Integer> entry :
                cashDrawer.entrySet())
            System.out.println(entry.getValue() + " of " + entry.getKey() + "'s");
    }

    public static void displayTotalInAccount() {
        for (Map.Entry<Integer, Integer> entry :
                cashDrawer.entrySet())
            total = total + (entry.getValue() * entry.getKey());
        System.out.println("Your current balance is " + total + " dollars.");
        continueTranscation();
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
