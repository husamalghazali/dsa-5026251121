package lw06.prelab;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BSTPrelab {

    // Insert a value into the BST (duplicates are ignored)
    static Node insert(Node root, int data) {
        if (root == null) {
            return new Node(data);
        }
        if (data < root.data) {
            root.left = insert(root.left, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        }
        return root;
    }

    // Search a target value in the BST
    static boolean search(Node root, int target) {
        if (root == null) {
            return false;
        }
        if (target == root.data) {
            return true;
        }
        if (target < root.data) {
            return search(root.left, target);
        }
        return search(root.right, target);
    }

    // Root -> Left -> Right
    static void preorder(Node root, StringBuilder sb) {
        if (root == null) return;
        sb.append(root.data).append(" ");
        preorder(root.left, sb);
        preorder(root.right, sb);
    }

    // Left -> Root -> Right
    static void inorder(Node root, StringBuilder sb) {
        if (root == null) return;
        inorder(root.left, sb);
        sb.append(root.data).append(" ");
        inorder(root.right, sb);
    }

    // Left -> Right -> Root
    static void postorder(Node root, StringBuilder sb) {
        if (root == null) return;
        postorder(root.left, sb);
        postorder(root.right, sb);
        sb.append(root.data).append(" ");
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("src/lw06/prelab/treenumbers.txt"));

        String[] values = sc.nextLine().trim().split("\\s+");
        int target = Integer.parseInt(sc.nextLine().trim());
        sc.close();

        Node root = null;
        for (String v : values) {
            root = insert(root, Integer.parseInt(v));
        }

        StringBuilder sb = new StringBuilder();
        preorder(root, sb);
        System.out.println("Preorder : " + sb.toString().trim());

        sb = new StringBuilder();
        inorder(root, sb);
        System.out.println("Inorder : " + sb.toString().trim());

        sb = new StringBuilder();
        postorder(root, sb);
        System.out.println("Postorder : " + sb.toString().trim());

        System.out.println("Search " + target + " : " + search(root, target));
    }
}
    

