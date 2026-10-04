package lw03.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        problem1();
        System.out.println();
        problem2();
        System.out.println();
        problem3();
    }

    // Cari file input di folder kerja saat ini; kalau tidak ada, coba folder src/lw03/prelab
    // (supaya tetap jalan saat dijalankan dari root project lewat VS Code)
    static File resolve(String name) {
        File f = new File(name);
        if (f.exists()) return f;
        return new File("src/lw03/prelab/" + name);
    }

    // Problem 1: Playlist (List)
    static void problem1() {
        List<String> playlist = new ArrayList<>();

        try (Scanner sc = new Scanner(resolve("playlist.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                // nama lagu bisa mengandung spasi, jadi split dibatasi
                String[] parts = line.split(" ", 2);
                String type = parts[0];

                switch (type) {
                    case "ADD" -> playlist.add(parts[1]);
                    case "INSERT" -> {
                        String[] p = parts[1].split(" ", 2); // [index, song]
                        int index = Integer.parseInt(p[0]);
                        playlist.add(index, p[1]);
                    }
                    case "REMOVE" -> playlist.remove(parts[1]); // hapus kemunculan pertama; kalau tidak ada, tidak terjadi apa-apa
                    default -> { }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File playlist.txt tidak ditemukan.");
            return;
        }

        System.out.println("===== Problem 1 =====");
        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }
    }

    // Problem 2: Workshop participants (Set)
    static void problem2() {
        Set<String> participants = new LinkedHashSet<>(); // menjaga urutan kemunculan pertama
        int duplicates = 0;

        try (Scanner sc = new Scanner(resolve("participants.txt"))) {
            while (sc.hasNextLine()) {
                String name = sc.nextLine().trim();
                if (name.isEmpty()) continue;

                if (!participants.add(name)) { // add() return false kalau sudah ada
                    duplicates++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File participants.txt tidak ditemukan.");
            return;
        }

        System.out.println("===== Problem 2 =====");
        System.out.println("Unique participants: " + participants.size());
        int no = 1;
        for (String name : participants) {
            System.out.println(no + ". " + name);
            no++;
        }
        System.out.println("Duplicate registrations: " + duplicates);
    }

    // Problem 3: Product inventory (Map)
    static void problem3() {
        Map<String, Integer> stock = new LinkedHashMap<>(); // menjaga urutan produk pertama kali muncul
        int failedSales = 0;

        try (Scanner sc = new Scanner(resolve("inventory.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");
                String type = parts[0];
                String product = parts[1];
                int qty = Integer.parseInt(parts[2]);

                switch (type) {
                    case "ADD" -> stock.put(product, stock.getOrDefault(product, 0) + qty);
                    case "SELL" -> {
                        if (stock.containsKey(product) && stock.get(product) >= qty) {
                            stock.put(product, stock.get(product) - qty);
                        } else {
                            failedSales++;
                        }
                    }
                    default -> { }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File inventory.txt tidak ditemukan.");
            return;
        }

        System.out.println("===== Problem 3 =====");
        for (Map.Entry<String, Integer> e : stock.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
        System.out.println("Failed sales: " + failedSales);
    }
}