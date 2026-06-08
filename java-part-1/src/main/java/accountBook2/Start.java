package accountBook2;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);

            int menu = 0;
            while (true){
                System.out.println("===== 가계부 =====");
                System.out.println("1. 내역 추가");
                System.out.println("2. 내역 조회");
                System.out.println("3. 삭제");
                System.out.println("4. 종료");
                System.out.println("번호 입력 > ");
                String input = sc.nextLine();
                if(input.matches("\\d+")){
                    menu = Integer.parseInt(input);
                    break;
                }else{
                    System.out.println("잘못된 입력 입니다. 다시 입력해 주세요");
                    continue;
                }
            }
            AccountBook book = new AccountBookImpl();
            switch(menu){
                case 1 :
                    book.addAccount();
                    break;
                case 2 :
                    book.showAccount();
                    break;
                case 3 :
                    book.deleteAccount();
                    break;
                case 4 :
                    System.out.println("종료합니다."); return;
                default :
                    System.out.println("잘못된 번호입니다.");
            }
        }


}
