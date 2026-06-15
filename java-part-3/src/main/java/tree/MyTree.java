package tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class MyTree {

    class Node{
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public void insert(int value) {
        // root부터 시작해서 노드를 추가하고, 새로 갱신된 root를 저장합니다.
        this.root = insertNode(root, value);
    }

    private Node insertNode(Node node, int value){
        if(node == null) return new Node(value);
        if(value < node.value) node.left = insertNode(node.left, value);
        else if (value > node.value) node.right = insertNode(node.right, value);
        // 같은 값인 경우 무시
        return node;
    }

    public void preOrder(){
        System.out.println("전위: ");
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node node){
        if(node == null) return;
        System.out.println(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder(){
        System.out.println("중위: ");
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node){
        if(node == null) return;
        inOrder(node.left);
        System.out.println(node.value + " ");
        inOrder(node.right);
    }

    public void postOrder(){
        System.out.println("후위: ");
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node node){
        if(node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.value + " ");
    }

    public void BFS() {
        if(root == null) return;

        Queue<Node> q = new ArrayDeque<>();
        q.add(root);

        int level = 1;

        while(!q.isEmpty()){
            int levelSize = q.size();
            Node current = q.poll();
            System.out.print(current.value + " ");

            if (current.left != null) q.add(current.left);
            if (current.right != null) q.add(current.right);


        }
    }

}
