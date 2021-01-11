import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Tree tree = new Tree();
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] str = s.split(" ");
        int[] values = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            values[i] = Integer.parseInt(str[i]);
        }
        for (int i = 0; i < values.length; i++) {
           tree.add(values[i]);
        }
        System.out.println("preorder");
        tree.preorder();
        System.out.println("inorder");
        tree.inorder();
        System.out.println("postorder");
        tree.postorder();
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }

        public Node add(int value) {
            if (value > this.value) {
                if (this.right == null) {
                    this.right = new Node(value);
                    return this.right;
                } else {
                    return this.right.add(value);
                }
            } else {
                if (this.left == null) {
                    this.left = new Node(value);
                    return this.left;
                } else {
                    return this.left.add(value);
                }
            }
        }
    }

    public static class Tree {
        public Node root;

        public void add(int value) {
            if (root == null) {
                root = new Node(value);
            } else {
                root.add(value);
            }
        }

        void preorder(Node node) {
            if (node == null)
                return;
            System.out.println(node.value);
            preorder(node.left);
            preorder(node.right);
        }

        void preorder() {
            preorder(root);
        }

        void inorder(Node node) {
            if (node == null)
                return;
            inorder(node.left);
            System.out.println(node.value);
            inorder(node.right);
        }

        void inorder() {
            inorder(root);
        }

        void postorder(Node node) {
            if (node == null)
                return;
            postorder(node.left);
            postorder(node.right);
            System.out.println(node.value);
        }

        void postorder() {
            postorder(root);
        }

    }
}