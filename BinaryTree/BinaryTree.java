package BinaryTree;

public class BinaryTree {
    static boolean flag = true;

    static class Tree {
        Tree prev;
        Tree next;

        int data;

        Tree() {

        }

        Tree(int data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private boolean isBinary(Tree root) {
        if (root == null) {
            return true;
        }
        if (root.prev != null && root.prev.data >= root.data) {
            return false;
        }
        if (root.next != null && root.next.data < root.data) {
            return false;
        }
        if (!isBinary(root.prev) || !isBinary(root.next))
            return false;

        return true;

    }


    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();

        Tree root = new Tree(100);
        root.prev = new Tree(50);
        root.next = new Tree(200);
        root.prev.prev = new Tree(20);
        root.prev.prev.prev = new Tree(10);
        root.prev.next = new Tree(70);
        root.prev.next.prev = new Tree(60);
        root.prev.next.next = new Tree(75);
        root.next.prev = new Tree(110);
        root.next.next = new Tree(250);
        root.next.next.prev = new Tree(220);
        root.next.next.next = new Tree(280);


        System.out.println(bt.isBinary(root));
    }
}