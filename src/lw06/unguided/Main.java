package lw06.unguided;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("src/lw06/unguided/treenumbers.txt"));
        String[] nums = sc.nextLine().trim().split("\\s+");
        int target = Integer.parseInt(sc.nextLine().trim());
        sc.close();

        BinarySearchTree bst = new BinarySearchTree();
        for (String n : nums) {
            bst.insert(Integer.parseInt(n));
        }

        System.out.print("Preorder : ");
        bst.preOrder();
        System.out.print("Inorder : ");
        bst.inOrder();
        System.out.print("Postorder : ");
        bst.postOrder();
        System.out.println("Search " + target + " : " + bst.search(target));
        System.out.println("Minimum : " + bst.findMin());
        System.out.println("Maximum : " + bst.findMax());
    }
}
