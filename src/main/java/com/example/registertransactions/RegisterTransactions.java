package com.example.registertransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class RegisterTransactions {
    public static Scanner scanner = new Scanner(System.in);
    public static TreeMap<Integer, Integer> cashDrawer = new TreeMap<>();
    public static int total = 0;

    public static void continueTransaction(String input) {
        switch (input.toLowerCase()) {
            case "yes":
                System.out.println("Okay, let's start another transaction for you.");
                transactionInput();
                break;
            case "no":
                System.out.println("Thanks for visiting, your transaction is complete");
                break;
            default:
                break;
        }
    }

    public static void deposit() {
        System.out.println("How much money (in dollars) do you want to deposit today?");
        int depositAmount = scanner.nextInt();
        int tempBalance = 0;

        if (depositAmount > 0) {
            System.out.println("Okay, I can deposit up to " + depositAmount + " dollars for you.");
            System.out.println("For each bill type please specify how many you have to deposit");
            for (Integer i : cashDrawer.keySet()) {
                System.out.println("Please input how many " + i + "'s");
                int billAmount = scanner.nextInt();
                tempBalance = (depositAmount - (i * billAmount));
                int currentNumber = cashDrawer.get(i);
                cashDrawer.put(i, currentNumber + billAmount);
                depositAmount = depositAmount - (i * billAmount);
            }
            displayBalanceOfEach(cashDrawer);
            displayTotalInAccount(cashDrawer);
            System.out.println("Do you want change back from your account deposit? yes/no");
            String answer = scanner.next();
            if (answer.equals("yes")) {
                withdrawal();
            } else {
                System.out.println("Do you want to make another request? Choose yes or no.");
                String yesOrNo = scanner.next();
                continueTransaction(yesOrNo);
            }
        } else {
            System.out.println("You chose to deposit 0 dollars. Let's Try again.");
            transactionInput();
        }
    }

    public static void withdrawal() {
        System.out.println("How much money (in dollars) do you want to withdraw today?");
        int withdrawAmount = scanner.nextInt();

        if (total > withdrawAmount) {
            System.out.println("Okay, I can withdraw " + withdrawAmount + " dollars for you.");
            System.out.println("For each bill type please specify how many you want to with draw, but no more than " + withdrawAmount + " dollars.");
            for (Integer i : cashDrawer.keySet()) {
                System.out.println("Please input how many " + i + "'s");
                int billAmount = scanner.nextInt();
                int currentNumber = cashDrawer.get(i);
                cashDrawer.put(i, currentNumber - billAmount);
                withdrawAmount = withdrawAmount - (i * billAmount);
            }
            displayBalanceOfEach(cashDrawer);
            displayTotalInAccount(cashDrawer);
            System.out.println("Do you want to make another request? Choose yes or no.");
            String yesOrNo = scanner.next();
            continueTransaction(yesOrNo);
        } else {
            System.out.println("Your current balance is " + total + " dollars. Please try again within that amount.");
            transactionInput();

        }
    }

    public static void displayBalanceOfEach(TreeMap<Integer, Integer> treeMap) {
        for (Map.Entry<Integer, Integer> entry :
                treeMap.entrySet())
            System.out.println(entry.getValue() + " of " + entry.getKey() + "'s");
    }

    public static void displayTotalInAccount(TreeMap<Integer, Integer> treeMap) {
        total = 0;
        for (Map.Entry<Integer, Integer> entry :
                treeMap.entrySet())
            total = total + (entry.getValue() * entry.getKey());
        System.out.println("Your current balance is " + total + " dollars.");

    }

    public static List<Integer> bills = Arrays.asList(20, 10, 5, 2, 1);

    public static void listAllWaysToMakeChange(int sumSoFar, int minBill, String changeSoFar, List<TreeMap<Integer, Integer>> listOfAllCombos, TreeMap<Integer, Integer> cashDrawerKeyValues) {
        String toReturn = "";
        for (int i = minBill; i < bills.size(); i++) {
            String change = changeSoFar;
            int sum = sumSoFar;

            while (sum > 0) {
                if (!String.valueOf(change).isEmpty()) change += " + ";
                change += bills.get(i);
                    cashDrawerKeyValues.put(bills.get(i), cashDrawerKeyValues.get(bills.get(i)) + 1);
                sum -= bills.get(i);
                if (sum > 0) {
                    listAllWaysToMakeChange(sum, i + 1, change, listOfAllCombos, cashDrawerKeyValues);
                }
            }

            if (sum == 0) {
//                This line will print a String of the possible values from the greatest bill to the least greatest
//                Example: The input sumSoFar on the first occurrence is the total inputted by the user
//                Input = 15
//                This line prints at index of 1
//                10 + 5
//                System.out.println(change);
                TreeMap<Integer, Integer> addThisToListAll = new TreeMap<>(cashDrawerKeyValues);
                listOfAllCombos.add(addThisToListAll);
                cashDrawerKeyValues.clear();
                cashDrawerKeyValues.put(1, 0);
                cashDrawerKeyValues.put(2, 0);
                cashDrawerKeyValues.put(5, 0);
                cashDrawerKeyValues.put(10, 0);
                cashDrawerKeyValues.put(20, 0);
            }
        }
    }


    public static void transactionInput(){
        System.out.println("Do you want to deposit (put), withdrawal (take), cashback (change), or get balance (show)?");
        String transactionType = scanner.next();

        switch(transactionType.toLowerCase()) {
            case "deposit" :
                deposit();
                break;
            case "withdrawal" :
                withdrawal();
                break;
            case "cashback" :
                System.out.println("Input the amount you want in small bills. Example: 15 or 80.");
                int cashAmount = scanner.nextInt();
                List<TreeMap<Integer, Integer>> listOfAllCombos = new ArrayList<>();
                TreeMap<Integer, Integer> cashDrawerKeyValues = new TreeMap<>();
                cashDrawerKeyValues.put(1, 0);
                cashDrawerKeyValues.put(2, 0);
                cashDrawerKeyValues.put(5, 0);
                cashDrawerKeyValues.put(10, 0);
                cashDrawerKeyValues.put(20, 0);
                listAllWaysToMakeChange(cashAmount, 1, "", listOfAllCombos, cashDrawerKeyValues);
                TreeMap<Integer, Integer> firstComboToPick = listOfAllCombos.get(0);
                displayBalanceOfEach(firstComboToPick);
                break;
            case "balance" :
                displayBalanceOfEach(cashDrawer);
                displayTotalInAccount(cashDrawer);
                System.out.println("Do you want to make another request? Choose yes or no.");
                String yesOrNo = scanner.next();
                continueTransaction(yesOrNo);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(RegisterTransactions.class, args);
        System.out.println("Hello, Welcome to Bank of Utah!");
        cashDrawer.put(1, 0);
        cashDrawer.put(5, 0);
        cashDrawer.put(10, 0);
        cashDrawer.put(20, 0);
        transactionInput();

    }

}
