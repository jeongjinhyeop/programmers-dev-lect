package pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        List<Pet> petList = new ArrayList<>();
        Main m = new Main();
        while(true) {
            System.out.print("반려동물의 이름을 지어주세요: ");
            String name = sc.nextLine();

            System.out.print("반려동물의 종류를 정해주세요: ");
            String type = sc.nextLine();

            petList.add(new Pet(name, type));
            System.out.println(name + " 등록이 완료되었습니다.");

            boolean isContinue = true;
            //펫 추가등록 여부 확인
            while(true){
                System.out.print("추가로 등록 하시겠습니까? (y/n) > ");
                String inputYN = sc.nextLine().trim().toLowerCase();

                if(inputYN.equals("y")){
                    System.out.println("추가 등록을 진행합니다.");
                    break;
                }else if(inputYN.equals("n")){
                    System.out.println("펫 등록을 종료합니다.");
                    isContinue = false;
                    break;
                }else{
                    System.out.println("올바르지 않은 값 입니다. y/n 만 입력해주세요");
                }
            }

            if(!isContinue) break;
        }

        //2. 관리할 펫 고르기
        Pet targetPet = m.selectPet(petList, sc);
        System.out.println("현재 " + targetPet.getName() + "의 상태입니다.");
        targetPet.showStatus();

        while (true) {
            System.out.println("\n무엇을 할까요? [1]먹이주기 [2]놀아주기 [3]상태보기 [4]다른 펫 선택 [5] 종료");
            System.out.print("> ");
            int menu = Integer.parseInt(sc.nextLine());

            if (menu == 1)      { targetPet.feed();  targetPet.showStatus(); targetPet.defaultCost(targetPet.getName());}
            else if (menu == 2) { targetPet.play();  targetPet.showStatus(); targetPet.defaultCost(targetPet.getName());}
            else if (menu == 3) { targetPet.showStatus(); }
            else if (menu == 4) {
                targetPet = m.selectPet(petList, sc);
                System.out.println("현재 " + targetPet.getName() + "의 상태입니다.");
                targetPet.showStatus();
            }
            else if (menu == 5) { System.out.println("안녕!"); break; }
            else                { System.out.println("1~4 중에 골라주세요."); }
        }
    }

    public Pet selectPet(List<Pet> petList, Scanner sc){
        Pet targetPet = null;
        while (targetPet == null){
            System.out.println("\n관리할 펫 선택");
            System.out.print("선택할 펫의 이름: ");
            String targetPetName = sc.nextLine();
            System.out.print("선택할 펫의 종류: ");
            String targetPetType = sc.nextLine();

            for(Pet p : petList){
                if(targetPetName.equals(p.getName()) && targetPetType.equals(p.getType())){
                    targetPet = p;
                    break;
                }
            }
            if(targetPet == null) System.out.println("일치하는 펫을 찾지 못했습니다. 다시 입력해주세요");
        }

        return targetPet;
    }
}
