package lw03.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/lw03/unguided/enrollment.txt"));

        // Map: kode course -> jumlah enrollment (satu kode = satu record)
        Map<String, Integer> enrollment = new HashMap<>();
        // List: urutan kemunculan pertama tiap course
        List<String> courseOrder = new ArrayList<>();
        // List: hasil CHECK sesuai urutan input
        List<String> checkResults = new ArrayList<>();

        int rejected = 0;

        while (sc.hasNext()) {
            String op = sc.next();
            String code = sc.next();

            if (op.equals("CHECK")) {
                if (enrollment.containsKey(code)) {
                    checkResults.add(code + ": " + enrollment.get(code) + " students");
                } else {
                    checkResults.add(code + ": Not found");
                }
            } else {
                int count = sc.nextInt();

                if (count <= 0) {
                    rejected++;
                } else if (op.equals("REGISTER")) {
                    if (enrollment.containsKey(code)) {
                        enrollment.put(code, enrollment.get(code) + count);
                    } else {
                        enrollment.put(code, count);
                        courseOrder.add(code);
                    }
                } else if (op.equals("WITHDRAW")) {
                    if (enrollment.containsKey(code) && enrollment.get(code) >= count) {
                        enrollment.put(code, enrollment.get(code) - count);
                    } else {
                        rejected++;
                    }
                }
            }
        }
        sc.close();

         System.out.println("===== Enrollment Checks =====");
        for (int i = 0; i < checkResults.size(); i++) {
            System.out.println(checkResults.get(i));
        }

        System.out.println("===== Final Enrollment =====");
        for (int i = 0; i < courseOrder.size(); i++) {
            String code = courseOrder.get(i);
            System.out.println(code + ": " + enrollment.get(code) + " students");
        }
        System.out.println("Rejected operations: " + rejected);



    }
}