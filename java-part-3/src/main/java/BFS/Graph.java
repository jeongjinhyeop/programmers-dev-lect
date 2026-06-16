package BFS;

import java.util.*;

public class Graph {
    //배열 안에 리스트를 담는 구조
    private LinkedList<Integer>[] adjacencyList ;

    public Graph(int vertex){
        adjacencyList = new LinkedList[vertex + 1];
        for (int i = 0; i < adjacencyList.length; i++){
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public LinkedList<Integer>[] getAdjacencyList(){
        return adjacencyList;
    }

    public void addEdge(int v, int w){
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);
    }

    public void printGraph() {
        for (int i = 1; i < adjacencyList.length; i++) {
            System.out.println("Vertex " + i + " : ");
            for (Integer v : adjacencyList[i]){
                System.out.println(v + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        boolean[] visited = new boolean[9 + 1];
        Graph g = new Graph(9);

        int[] dist = new int[9 + 1];
        g.addEdge(1,2);
        g.addEdge(2,3);
        g.addEdge(3,4);
        g.addEdge(4,3);
        g.addEdge(5,6);
        g.addEdge(6,7);
        g.addEdge(7,8);
        g.addEdge(8,9);

        int[][] map = new int[10][10];
        //6. 인접 행렬 버전
        for (int i = 1; i <= 9; i++) {
            for(Integer j : g.getAdjacencyList()[i]){
                map[i - 1][j  - 1] = 1;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        for (int i = 0; i < map.length; i++) {
            sb.setLength(0);
            for (int j = 0; j < map[0].length; j++) {
                sb.append(map[i][j]).append(" ");
            }
            System.out.println(sb.toString());
        }

        // 1. 시작 지점 변경해보기
        int startVertex = 3;
        Deque<Integer> q = new ArrayDeque<>();
        visited[startVertex] = true;

        //3. 최단거리 저장 배열
        int count = 0;
        dist[startVertex]  = count;
        q.add(startVertex);
        System.out.println("정점" + startVertex + "에서 시작하는 BFS");
        System.out.println(startVertex + " 시작!");

        //4. 경로 복원
        int[] parent = new int[9 + 1];

        //5. 연결되지 않은 그래프
        for(int i= 1; i <= 9; i++) {
            q.add(i);
            visited[i] = true;

            while (!q.isEmpty()) {
                int cur = q.poll();
                for (int adj : g.getAdjacencyList()[cur]) {
                    if (!visited[adj]) {
                        q.add(adj);
                        visited[adj] = true;
                        System.out.println(adj + " 방문!");
                        dist[adj] = dist[cur] + 1;
                        parent[adj] = cur;
                    }
                }
            }
        }
        for (int i = 1; i < dist.length; i++) {
            System.out.println(i + "번 노드 방문 시간: " + dist[i]);
        }
    }
}

