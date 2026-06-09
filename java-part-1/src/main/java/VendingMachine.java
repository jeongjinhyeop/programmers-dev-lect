import java.util.Scanner;

public class VendingMachine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //static 제거
        VendingMachine vendingMachine = new VendingMachine();
        System.out.println("돈을 넣어주세요!");
        int money= sc.nextInt();

        //입금 확인
        if (money <= 0) {
            System.out.println("금액이 올바르지 않습니다. 프로그램을 종료합니다.");
            return;
        }
        System.out.println("입력 금액 : " + money);
        int change = money;
        //잔돈이 남아있는 경우 계속해서 실행
        while (change > 0) {
            System.out.println("원하시는 버튼을 눌러주세요!!");
            System.out.println("[1]콜라-500원 [2]사이다-700원 [3]환타-300원 [4]물-200원 [5]돈넣기 [6]종료");
            System.out.print("선택: ");
            int button = sc.nextInt();

            if (button < 1 || button > 6) {
                System.out.println("비정상적인 접근입니다. 다음에 다시 시도해주세요");
                System.out.println("잔액 반환 : " + change);
                break;
            }

            if (button == 6) {
                System.out.println("종료를 선택하셨습니다.");
                break;
            }

            if(button == 5){
                System.out.println("넣으실 금액을 입력해 주세요!");
                int extraMoney = sc.nextInt();
                if(extraMoney > 0) change += extraMoney;
                System.out.printf("잔액은 %d 입니다.", change);
                continue;
            }
            //static 제거 후 생성한 객체 참조하도록 변경
            int price = vendingMachine.drink(button);
            if(change < price){
                System.out.println("잔액이 부족합니다!");
            }else {
                //static 제거 후 생성한 객체 참조하도록 변경
                change -= vendingMachine.drink(button);
                System.out.printf("잔액은 %d 입니다.", change);
            }
        }
        System.out.println("\n--------------------------------------------------");
        System.out.println("잔액 반환 : " + change + "원");
        System.out.println("감사합니다. 다음에도 이용 부탁드립니다!");
        sc.close();
    }

    public int drink(int botton){
        if (botton == 1){
            System.out.println("콜라를 선택하셨습니다.");
            return 500;
        }
        else if (botton == 2){
            System.out.println("사이다를 선택하셨습니다.");
            return 700;
        }
        else if (botton == 3){
            System.out.println("환타를 선택하셨습니다.");
            return 300;
        }
        else if (botton == 4){
            System.out.println("물을 선택하셨습니다.");
            return 200;
        }
        return 0;
    }
}

