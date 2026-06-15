package tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        MyTree tree = new MyTree();
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) tree.insert(v);

//        tree.preOrder();
//        tree.inOrder();
//        tree.postOrder();
//
//
//        //BFS
//        tree.BFS();

        System.out.println("SEARCH: " + tree.search(20));
    }
}
