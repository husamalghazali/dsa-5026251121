package lw01.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("rentals.txt"))) {
            int n = scanner.nextInt();
            Rental[] rentals = new Rental[n];
            int[] units = new int[n];

            for (int i = 0; i < n; i++) {
                String type = scanner.next();
                String id = scanner.next();
                int days = scanner.nextInt();
                units[i] = scanner.nextInt();

                switch (type) {
                    case "LAPTOP" -> rentals[i] = new LaptopRental(id, days);
                    case "PROJECTOR" -> rentals[i] = new ProjectorRental(id, days);
                    default -> throw new IllegalArgumentException("Unknown type: " + type);
                }
            }

            // Single loop over Rental references: runtime polymorphism
            for (int i = 0; i < n; i++) {
                Rental r = rentals[i];
                System.out.println(r.getId() + " | " + r.label() + " | " + r.calculateCharge(units[i]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("rentals.txt not found");
        }
    }
}