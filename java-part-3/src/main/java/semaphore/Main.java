package semaphore;

public class Main {

    public static void main(String[] args) {
        //capa를 1로 변경하는 경우 Mutex와 동일한 결과
//        Dungeon dungeon = new Dungeon(1);
        Dungeon dungeon = new Dungeon(2);

        String[] names = { "전사", "마법사", "궁수", "도적", "성기사" };
        for (String name : names) {
            new Adventurer(dungeon, name).start();
        }
        new Adventurer(dungeon, "초보자").start();
    }
}
