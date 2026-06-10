package rpgGame;

public class Main {
    public static void main(String[] args) {
        Character hero = new Character("용사", 100, 25);
        Character[] monsters = {
                new Character("슬라임"),              // 기본 생성자
                new Character("고블린", 50, 8),       // 커스텀
                new Character("드래곤", 120, 25)      // 보스
        };

        for (Character m : monsters) {
            System.out.println("\n=== 다음 상대: ===");
            m.showStatus();
            while(!(m.getHp() == 0 && !hero.isAlive())) {
                hero.attack(m);
                m.showStatus();
                if(m.getHp() == 0){
                    System.out.println(m.getName() + "이 죽었습니다.");
                    break;
                }

                m.attack(hero);
                hero.showStatus();
                if(!hero.isAlive()){
                    System.out.println("게임 오버...");
                    break;
                }
            }
        }
        if(hero.isAlive()) System.out.println("\n클리어!");
    }
}
