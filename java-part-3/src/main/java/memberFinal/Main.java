package memberFinal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        MemberManager manager = MemberManager.load();

        if (manager == null) {
            PricePlan pricePlan = null;
            while (pricePlan == null) {
                System.out.println("[1]Lite:10 [2]Basic:20 [3]Premium:30");
                pricePlan = PricePlan.from(readInt(sc));

                if (pricePlan == null) {
                    System.out.println("1~3 중 선택하세요");
                }
            }

            manager = new MemberManager(pricePlan);
        }


        while(true) {
            System.out.println("\n[현재 " + manager.getSize() + "/" + manager.getCapacity() + "]");
            System.out.println("[1]추가 [2]메일조회 [3]이름조회 [4]전체 [5]수정 [6]삭제 [7]종료");
            int menu = readInt(sc);

            switch (menu) {
                case 1: {
                    if (manager.isFull()) {
                        System.out.println("정원이 찼습니다");
                        break;
                    }
                    System.out.println("등급 [1]일반 [2]VIP");
                    int grade = readInt(sc);
                    System.out.print("이름 > ");
                    String name  = sc.nextLine().trim();
                    System.out.print("이메일 > ");
                    String email = sc.nextLine().trim();
                    System.out.print("연락처 > ");
                    String phone = sc.nextLine().trim();
                    if (manager.existsEmail(email)) {
                        System.out.println("이미 있는 회원입니다.");
                        break;
                    }
                    Member m = (grade == 2) ? new VipMember(name, email, phone) : new NormalMember(name, email, phone);
                    manager.add(m);
                    System.out.println("추가되었습니다.");
                    break;
                }

                case 2: {
                    System.out.println("이메일을 입력해주세요");
                    String email = sc.nextLine();
                    Member findMemberByEmail =manager.findByEmail(email);
                    if(findMemberByEmail == null) {
                        System.out.println("존재하지 않는 이메일입니다.");
                    } else {
                        System.out.println("이름: " + findMemberByEmail.getName() + " 이메일: " + findMemberByEmail.getEmail() + " 핸드폰: " + findMemberByEmail.getPhone());
                    }
                    break;
                }
                case 3: {
                    System.out.println("이름을 입력해주세요");
                    String name = sc.nextLine();
                    Member findMemberByName =manager.findByName(name);
                    if(findMemberByName == null) {
                        System.out.println("존재하지 않는 이메일입니다.");
                    } else {
                        System.out.println("이름: " + findMemberByName.getName() + " 이메일: " + findMemberByName.getEmail() + " 핸드폰: " + findMemberByName.getPhone());
                    }
                    break;
                }
                case 4: {
                    manager.printAll();
                    break;
                }
                case 5: {
                    System.out.print("기존 이메일 > "); String email = sc.nextLine();
                    if (manager.findByEmail(email) == null) {
                        System.out.println("해당 메일로 존재하는 회원이 없습니다.");
                        break;
                    }
                    System.out.print("이름 > ");   String name  = sc.nextLine();
                    System.out.print("신규 이메일 > "); String newEmail = sc.nextLine();
                    System.out.print("연락처 > "); String phone = sc.nextLine();
                    manager.update(email, name, newEmail, phone);
                    System.out.println("변경 되었습니다.");
                    break;
                }
                case 6: {
                    System.out.println("이메일을 입력해주세요");
                    String email = sc.nextLine();
                    if(!manager.delete(email)) {
                        System.out.println("존재하지 않는 이메일입니다.");
                    } else {
                        System.out.println("삭제되었습니다.");
                    }
                    break;
                }
                case 7:
                    manager.save();
                    System.out.println("이용해주셔서 감사합니다."); return;
                default: System.out.println("1~7 중에서 선택하세요.");
            }
        }

    }

    static int readInt(Scanner sc){
        try{
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return  -1;
        }
    }
}
