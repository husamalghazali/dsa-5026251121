package lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        LinkedList<String[]> transactions = new LinkedList<>();
        LinkedList<String[]> customers = new LinkedList<>();

        // 1. Read and store transactions
        try (Scanner scanner = new Scanner(new File("src/lw02/prelab/transactions.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                String name = parts[0];
                String type = parts[1];
                String amount = parts[2];

                transactions.add(new String[] {name, type, amount});

                // 2. Create customer data (only add the first time a name appears)
                boolean exists = false;
                for (String[] customer : customers) {
                    if (customer[0].equals(name)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    customers.add(new String[] {name, "0"});
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File transactions.txt tidak ditemukan.");
            return;
        }

        // 3. Process transactions using Queue (FIFO)
        Queue<String[]> queue = new LinkedList<>();
        queue.addAll(transactions);

        // 4. Stack to store failed withdrawals (LIFO)
        Stack<String[]> failedTransactions = new Stack<>();

        while (!queue.isEmpty()) {
            String[] tx = queue.poll();
            String name = tx[0];
            String type = tx[1];
            int amount = Integer.parseInt(tx[2]);

            // find the corresponding customer
            String[] customer = null;
            for (String[] c : customers) {
                if (c[0].equals(name)) {
                    customer = c;
                    break;
                }
            }

            int balance = Integer.parseInt(customer[1]);

            if (type.equals("DEPOSIT")) {
                balance += amount;
                customer[1] = String.valueOf(balance);
            } else if (type.equals("WITHDRAW")) {
                if (amount > balance) {
                    // failed, balance unchanged
                    failedTransactions.push(tx);
                } else {
                    balance -= amount;
                    customer[1] = String.valueOf(balance);
                }
            }
        }

        // 5. Display final balances and failed transactions
        System.out.println("=== Final Balances ===");
        for (String[] customer : customers) {
            System.out.println(customer[0] + " : " + customer[1]);
        }

        System.out.println();
        System.out.println("=== Failed Transactions ===");
        while (!failedTransactions.isEmpty()) {
            String[] tx = failedTransactions.pop();
            System.out.println(tx[0] + " " + tx[1] + " " + tx[2]);
        }
    }
}