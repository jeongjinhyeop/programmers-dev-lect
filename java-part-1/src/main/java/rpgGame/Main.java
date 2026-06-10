package rpgGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Character hero = new Hero("용사", 100, 25, 7);
        Character[] monsters = {
                new Monster("슬라임"),              // 기본 생성자
                new Monster("고블린", 50, 8, 2),
                new Monster("드래곤", 120, 25, 5)
        };

        for (Character m : monsters) {
            System.out.println("\n=== 다음 상대: ===");
            m.showStatus();
            while(m.isAlive() && hero.isAlive()) {
                System.out.println("현재 상태");
                hero.showStatus();
                m.showStatus();

                boolean isDefending = false;
                while(true) {
                    System.out.println("행동을 고르세요 (1.공격 | 2.방어 | 3.회복): ");
                    int choice = Integer.parseInt(sc.nextLine());

                    if (choice == 1) {
                        hero.attack(m);
                        break;
                    } else if (choice == 2) {
                        System.out.println(hero.getName() + "이(가) 방어를 합니다!");
                        isDefending = true;
                        break;
                    } else if (choice == 3) {
                        hero.heal(30);
                        hero.showStatus();
                        break;
                    } else {
                        System.out.println();
                    }
                }

                if (!m.isAlive()) {
                    System.out.println(m.getName() + "이 죽었습니다.");
                    break;
                }

                System.out.println();
                if (isDefending) {
                    System.out.println("방어 성공! 몬스터의 공격력이 반감됩니다.");
                    int reducedDmg = m.getPower() / 2;
                    hero.takeDamage(reducedDmg);
                } else {
                    m.attack(hero);
                }

                // 영웅 사망 체크
                if (!hero.isAlive()) {
                    System.out.println("게임 오버...");
                    break;
                }
            }
            if (!hero.isAlive()) {
                break;
            }
        }
        if (hero.isAlive()) {
            System.out.println("\n클리어!");
        }
    }

}
