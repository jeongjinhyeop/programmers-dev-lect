package pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        
        System.out.print("반려동물의 이름을 지어주세요: ");
        String name = sc.nextLine();

        System.out.print("반려동물의 종류를 정해주세요: ");
        String type = sc.nextLine();

        Pet pet = new Pet(name, type);
        pet.showStatus();
        while (true) {
            System.out.println("\n무엇을 할까요? [1]먹이주기 [2]놀아주기 [3]상태보기 [4]종료");
            System.out.print("> ");
            int menu = Integer.parseInt(sc.nextLine());

            if (menu == 1)      { pet.feed();  pet.showStatus(); pet.defaultCost(name);}
            else if (menu == 2) { pet.play();  pet.showStatus(); pet.defaultCost(name);}
            else if (menu == 3) { pet.showStatus(); }
            else if (menu == 4) { System.out.println("안녕!"); break; }
            else                { System.out.println("1~4 중에 골라주세요."); }
        }
    }
}
