package treeMap;

public class MyTreeMap {
    class Node{
        String key;
        Integer value;
        Node left;
        Node right;

        Node(String key, Integer value){
            this.key = key;
            this.value = value;
        }
    }
    private Node root;
    private int size = 0;

    public void put(String key, Integer value){
        root = putNode(root, key, value);
    }

    private Node putNode(Node node, String key, Integer value){
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        //음수면 key 값이 작다는 것
        int cmp = key.compareTo(node.key);
        if(cmp < 0) node.left = putNode(node.left, key, value);
        else if (cmp > 0) node.right = putNode(node.right, key, value);
        else node.value = value;

        return node;
    }

    public Integer get(String key){
        Node node = root;
        while (node != null){
            int cmp = key.compareTo(node.key);
            if (cmp < 0) node = root.left;
            else if (cmp > 0) node = root.right;
            else return root.value;
        }

        return null;
    }

    public void printSorted() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder (Node node) {
        if(node == null) return;
        inOrder(node.left);
        System.out.print("[" + node.key + "=" + node.value + "] ");
        inOrder(node.right);
    }

    public int size() {
        return size;
    }

    public boolean containsKey(String key){
        Node node = root;
        while (node != null) {
            int cmp = key.compareTo(root.key);
            if (cmp < 0) node = root.left;
            else if (cmp > 0) node = root.right;
            else return true;
        }
        return false;
    }

    public String firstKey() {
        if (root == null) return null;
        Node n = root;
        while (n.left != null) n = n.left;
        return n.key;
    }

    public String lastKey() {
        if (root == null) return null;
        Node n = root;
        while (n.right != null) n = n.right;
        return n.key;
    }

    public Integer remove(String key){
        Integer old = get(key);
        if(old == null) return null;
        root = removeNode(root, key);
        size--;
        return old;
    }

    private Node removeNode(Node node, String key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node = node.left;
        else if (cmp > 0) node = node.right;
        else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node succ = node.right;
                while (succ.left != null) succ = succ.left;
                node.key = succ.key;
                node.value = succ.value;
                node.right = removeNode(node.right, succ.key);
            }

        }
            return node;

    }
}
