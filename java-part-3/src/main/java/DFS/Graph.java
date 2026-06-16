package DFS;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class Graph {
    private LinkedList<Integer>[] adjacencyList;

    public Graph(int vertex){
        adjacencyList = new LinkedList[vertex + 1];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w){
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);
    }

    public void dfs(int start){
        boolean[] visited = new boolean[adjacencyList.length];
        System.out.println("정점 " + start + "에서 시작하는 DFS");
        dfsRecursive(start, visited);

    }

    private void dfsRecursive (int vertex, boolean[] visited){
        visited[vertex] = true;
        System.out.println(vertex + " ");

        for(int adj : adjacencyList[vertex]){
            if(!visited[adj]){
                dfsRecursive(adj, visited);
            }
        }
    }



    public static void main(String[] args) {
        Graph graph = new Graph(9);
//        graph.addEdge(1, 2);
//        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(2, 6);
        graph.addEdge(3, 7);
        graph.addEdge(4, 5);
        graph.addEdge(4, 7);
        graph.addEdge(4, 8);
        graph.addEdge(5, 6);
        graph.addEdge(7, 8);
        graph.addEdge(8, 9);

//        graph.dfs(1);   // 1번에서 깊이 우선 탐색

        Deque<Integer> stack = new ArrayDeque<>();
        boolean[] visited = new boolean[10];
        int count = 0;

        int[] parent = new int[10];

        int targetNode = 1;
        boolean isPathFound = false;

        for (int i = 1; i <= 9; i++) {
            if(!visited[i]) count++;
            stack.push(i);

            while (!stack.isEmpty()) {
                int cur = stack.pop();

                if (visited[cur]) continue;
                visited[cur] = true;

                //for문 사용으로 인해 무조건 true
//                if (targetNode == cur){
//                    isPathFound = true;
//                }

                for (int next : graph.adjacencyList[cur]) {
                    if (!visited[next]) {
                        parent[cur] = next;
                        stack.push(next);
                    } else {
                        if (cur != parent[next]) {
                            System.out.println("사이클 존재");
                        }
                    }
                }
            }
            System.out.println(isPathFound);

        }
        System.out.println("끊긴 그래프 연결요소 수!: " + count);

    }
}
