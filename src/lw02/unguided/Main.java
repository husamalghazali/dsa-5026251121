package lw02.unguided;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static final int MAX_BORROW = 2;

    public static void main(String[] args) {
        // LinkedList untuk semua request: {nama, judul}
        LinkedList<String[]> requests = new LinkedList<>();

        try {
            Scanner fileScanner = new Scanner(new File("src/lw02/unguided/borrowing.txt"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                String name = lineScanner.next();
                String title = lineScanner.next();
                requests.add(new String[]{name, title});
                lineScanner.close();
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File borrowing.txt tidak ditemukan.");
            return;
        }

        // 2. LinkedList buku: {judul, stok}
        LinkedList<String[]> books = new LinkedList<>();
        books.add(new String[]{"Kalkulus", "2"});
        books.add(new String[]{"Fisika", "1"});
        books.add(new String[]{"Statistika", "2"});

        // LinkedList member: {nama, jumlahPinjam}, urut kemunculan pertama
        LinkedList<String[]> members = new LinkedList<>();
        for (int i = 0; i < requests.size(); i++) {
            String name = requests.get(i)[0];
            boolean exists = false;
            for (int j = 0; j < members.size(); j++) {
                if (members.get(j)[0].equals(name)) {
                    exists = true;
                }
            }
            if (!exists) {
                members.add(new String[]{name, "0"});
            }
        }

        // 3. Pindahkan semua request ke Queue
        Queue<String[]> queue = new LinkedList<>();
        for (int i = 0; i < requests.size(); i++) {
            queue.add(requests.get(i));
        }

        LinkedList<String[]> successList = new LinkedList<>();
        Stack<String[]> failedStack = new Stack<>();

        // Proses FIFO sampai queue kosong
        while (!queue.isEmpty()) {
            String[] req = queue.poll();

            String[] book = null;
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i)[0].equals(req[1])) {
                    book = books.get(i);
                }
            }

            String[] member = null;
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i)[0].equals(req[0])) {
                    member = members.get(i);
                }
            }

            int stock = Integer.parseInt(book[1]);
            int borrowed = Integer.parseInt(member[1]);

            if (stock > 0 && borrowed < MAX_BORROW) {
                book[1] = String.valueOf(stock - 1);
                member[1] = String.valueOf(borrowed + 1);
                successList.add(req);
            } else {
                failedStack.push(req); // 4. gagal -> Stack
            }
        }

        // 5. Tampilkan hasil
        System.out.println("=== Successfully Processed Requests ===");
        for (int i = 0; i < successList.size(); i++) {
            System.out.println(successList.get(i)[0] + " " + successList.get(i)[1]);
        }

        System.out.println();
        System.out.println("=== Remaining Book Stock ===");
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i)[0] + " : " + books.get(i)[1]);
        }

        System.out.println();
        System.out.println("=== Failed Requests ===");
        while (!failedStack.isEmpty()) {
            String[] f = failedStack.pop(); // LIFO
            System.out.println(f[0] + " " + f[1]);
        }
    }
}

        