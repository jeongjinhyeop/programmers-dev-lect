package UpDownGame;

import java.util.Scanner;

public class UpDownGameMain {
    public static Scanner sc = new Scanner(System.in);
    public static int checkCnt = 0;
    public static int minCnt = Integer.MAX_VALUE;

    public static int choiceRandomNum(int level){
//        int answer = (int) (Math.random() * level) + 1;
//        System.out.println(answer);
//
//        return answer;
        return (int) (Math.random() * level) + 1;
    }

    public static boolean checkAnswer(int answer, int level){
        System.out.print("입력 > ");
        String input = sc.nextLine();
        if(!input.matches("\\d+")) {
            System.out.println("올바르지 않은 형식입니다.");
        }

        int guessNum = Integer.parseInt(input);

        if(guessNum < 1 || guessNum > level){
            System.out.printf("1~%d 사이로 입력해주세요\n", level);
        }

        if (checkCnt == 7 && answer != guessNum) {
            System.out.println("기회 7번을 모두 사용하셨습니다.");
            return false;
        }

        if(answer == guessNum) {
            System.out.printf("정답입니다! %d번 만에 맞혔어요.", checkCnt);
            return true;
        }else if (answer > guessNum){
            checkCnt++;
            System.out.println("UP! 더 큰 수입니다.");
            return false;
        } else {
            checkCnt++;
            System.out.println("DOWN! 더 작은 수입니다.");
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){

            //난이도 나중에 적용하기
            System.out.println("난이도를 선택해 주세요!");
            String input = sc.nextLine();
            if(!input.matches("\\d+")) {
                System.out.println("숫자를 입력해주세요.");
                continue;
            }
            int level = Integer.parseInt(input) * 100;
            int answer = choiceRandomNum(level);

            System.out.println("숫자를 맞혀보세요!");
            checkCnt = 1;

            while (!checkAnswer(answer, level)){
               if(checkCnt == 7) {
                   System.out.printf("기회 %d를 모두 사용하셨습니다.", checkCnt);
                   break;
               }
            }
            //최고 기록 갱신
            minCnt = Math.min(checkCnt, minCnt);
            if (minCnt == Integer.MAX_VALUE) {
                System.out.println("최고 기록이 존재하지 않습니다.");
            }else{
                System.out.printf("현재 최고 기록은 %d 입니다.\n", minCnt);
            }
            //한판 더?
            System.out.println("한판 더 ?");
            System.out.println("입력 > ");
            String yn = sc.nextLine();

            if (yn.equals("y")){
                System.out.println("한판 더!");
            } else if (yn.equals("n")) {
                System.out.println("종료합니다.");
                return;
            } else {
                System.out.println("올바르지 않은 입력입니다.");
                System.out.println("종료합니다.");
                return;
            }
        }
    }
}
