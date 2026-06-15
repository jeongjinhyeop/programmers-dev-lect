package hashMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MyHashMap {
    static class Node{
        String key;
        String value;
        Node next;

        public Node(String key, String value){
            this.key = key;
            this.value = value;
        }
    }

    private Node[] buckets;
    private int capacity = 16;
    private int size = 0;
    private final Double LOAD_FACTOR = 0.75;

    public MyHashMap() {
        buckets = new Node[capacity];
    }

    public int getIndex(String key){
//        return Math.abs(key.hashCode()) % capacity;
        return (key.hashCode() & 0x7fffffff) % capacity;
    }
    // 해시 인덱스를 가져와서 해당 인덱스를 가진 배열을 쭉 돌아보며 키가 같은 Node가 있는지 확인
    // 똑같은 키가 존재하면 value값 덮어쓰기
    // 존재하지 않는 경우 버킷에서 자기 인덱스의 노드 배열을 불러와서
    public void put(String key, String value){
        int idx = getIndex(key);
        Node head = buckets[idx];

        for(Node n = head; n != null; n = n.next){
            if(n.key.equals(key)) {
                n.value = value;
                return;
            }
        }

        if (size >= capacity * LOAD_FACTOR) {
            reSize();
        }

        Node node = new Node(key, value);
        node.next = head; //왜 헤드를 next로 설정하는거지? 새로 들어온 애 일수록 뒤에 배치아닌가
        buckets[idx] = node;
        size++;
    }

    public String get(String key){
        int idx = getIndex(key);
        for(Node n = buckets[idx]; n != null; n = n.next){
            if(n.key.equals(key)) {
                return n.value;
            }
        }
        return null;
    }

    public int size(){
        return size;
    }

    public boolean containKey(String key){
        int idx = getIndex(key);
        for(Node n = buckets[idx]; n != null; n = n.next){
            if(n.key.equals(key)) return true;
        }
        return false;
    }

    public String remove(String key){
        int idx = getIndex(key);
        Node n = buckets[idx];
        Node prev = null;

        while(n != null){
            if(n.key.equals(key)){
                if (prev == null) buckets[idx] = n.next;
                else              prev.next = n.next;
                size--;
                return n.value;
            }
            prev = n;
            n = n.next;
        }

        return null;
    }

    private void reSize() {
        capacity = capacity * 2;
        Node[] newBuckets = new Node[capacity];

        for (Node bucket : buckets) {
            Node current = bucket;

            while (current != null) {
                Node nextNode = current.next;
                int newIdx = Math.abs(current.key.hashCode() & 0x7fffffff) % capacity;

                current.next = newBuckets[newIdx];
                newBuckets[newIdx] = current;

                current = nextNode;
            }
        }
        this.buckets = newBuckets;
    }

    public ArrayList<String> keySet(){
        ArrayList<String> keys = new ArrayList<>();

        for(int i = 0; i < buckets.length; i++){
            Node current = buckets[i];

            while (current != null){
                keys.add(current.key);
                current = current.next;
            }
        }
        return keys;
    }

    public ArrayList<String> values(){
        ArrayList<String> values = new ArrayList<>();

        for(int i = 0; i < buckets.length; i++){
            Node current = buckets[i];

            while(current != null){
                values.add(current.value);
                current = current.next;
            }
        }
        return values;
    }

    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();
        map.put("test42", "123");
        map.put("test123", "234");
        System.out.println(map.getIndex("test")); // 계속 2 출력
        System.out.println(map.getIndex("test1")); // 계속 2 출력
        System.out.println(map.getIndex("test42")); // 계속 2 출력
        System.out.println(map.getIndex("test123")); // 계속 2 출력
        System.out.println(map.getIndex("a")); // 계속 2 출력
        System.out.println(map.getIndex("tv")); // 계속 2 출력
        System.out.println(map.getIndex("tr")); // 계속 2 출력
        System.out.println(map.getIndex("e")); // 계속 2 출력
        System.out.println(map.getIndex("y")); // 계속 2 출력

        String a = map.get("test42");
        String b = map.get("test123");
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println( map.remove("test"));
        System.out.println(map.size);

    }
}
