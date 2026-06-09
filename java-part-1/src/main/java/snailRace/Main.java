package snailRace;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Race race = new Race();

        System.out.println("난이도를 선택하세요 (1: 쉬움 | 2: 보통 | 3: 어려움)");
        System.out.print("입력 > ");
        int difficulty = Integer.parseInt(sc.nextLine());

        int distance = 30;
        int speed = 300;

        switch (difficulty) {
            case 1:
                distance = 15;
                speed = 400;
                break;
            case 2:
                distance = 30;
                speed = 250;
                break;
            case 3:
                distance = 50;
                speed = 100;
                break;
            default:
                System.out.println("잘못된 입력입니다. 보통 난이도로 시작합니다.");
                break;
        }

        //달팽이 수 세팅
        System.out.println("몇 마리로 경주를 시작하시겠습니까?");
        int count = Integer.parseInt(sc.nextLine());

        //배팅할 번호 결정
        System.out.println("몇번에 배팅하시겠습니까?");
        System.out.printf("범위는 1 ~ %s 입니다.", count);
        int batting = Integer.parseInt(sc.nextLine());

        List<Snail> snailList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Snail snail = new Snail("달팽이 " + i, i, race, distance, speed);
            snailList.add(snail);
        }

        for (Snail snail : snailList) {
            snail.start();
        }

        RaceRenderer renderer = new RaceRenderer(snailList, count, speed);
        renderer.start();

        try {
            renderer.join();
            for (Snail snail : snailList) {
                snail.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Collections.sort(snailList, new Comparator<Snail>() {
            @Override
            public int compare(Snail s1, Snail s2) {
                return Integer.compare(s1.getRank(), s2.getRank());
            }
        });

        for (Snail s : snailList){
            System.out.println(s.getRank() + "등 : " + s.getSnailName());
        }

        if(batting == race.getWinnerNum()) {
            System.out.println("\n배팅에 성공하셨습니다.");
        }else{
            System.out.println("\n배팅에 실패하셨습니다.");
        }

    }

}
