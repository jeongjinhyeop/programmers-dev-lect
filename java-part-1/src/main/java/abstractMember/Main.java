package abstractMember;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("[요금제를 선택하세요]");
        System.out.println("[1]Lite : 10 명 [2]Basic : 20 명 [3]Premium : 30 명");
        int plan = Integer.parseInt(sc.nextLine());
        MemberManager manager = new MemberManager(plan * 10);

        while (true) {
            System.out.println("\n[수행할 업무를 선택하세요 - 현재 회원수 : " + manager.getCount() + "/" + manager.getCapacity() + "]");
            System.out.println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)");
            System.out.println("[4]전체조회 [5]수정 [6]삭제 [7]정렬메뉴 [8]종료");
            System.out.print("> ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    if (manager.isFull()) {
                        System.out.println("회원이 꽉 찼습니다.");
                    } else {
                        System.out.println("등급을 선택하세요. [1]일반 [2]VIP");
                        int grade = Integer.parseInt(sc.nextLine());
                        System.out.print("이름 > ");   String name = sc.nextLine();
                        System.out.print("이메일 > "); String email = sc.nextLine();
                        System.out.print("연락처 > "); String phone = sc.nextLine();

                        if (manager.existsEmail(email)) {
                            System.out.println("이미 존재하는 회원(이메일)입니다.");
                        } else {
                            Member m;
                            if (grade == 2) m = new VipMember(name, email, phone);
                            else m = new NormalMember(name, email, phone);

                            manager.add(m);
                            System.out.println("회원 등록 및 파일 저장이 완료되었습니다.");
                        }
                    }
                    break;

                case 2:
                    System.out.print("조회할 이메일 입력 > ");
                    String searchEmail = sc.nextLine();
                    Member foundByEmail = manager.findByEmail(searchEmail);
                    if (foundByEmail == null) System.out.println("찾으시는 정보가 없습니다.");
                    else foundByEmail.printInfo();
                    break;

                case 3:
                    System.out.print("조회할 이름 입력 > ");
                    String searchName = sc.nextLine();
                    Member foundByName = manager.findByName(searchName);
                    if (foundByName == null) System.out.println("찾으시는 정보가 없습니다.");
                    else foundByName.printInfo();
                    break;

                case 4:
                    manager.printAll();
                    break;

                case 5:
                    System.out.print("수정할 회원의 기존 이메일 입력 > ");
                    String targetEmail = sc.nextLine();
                    Member targetMember = manager.findByEmail(targetEmail);

                    if (targetMember == null) {
                        System.out.println("찾으시는 회원이 없습니다.");
                    } else {
                        System.out.print("변경할 이름 > ");   String newName = sc.nextLine();
                        System.out.print("변경할 이메일 > "); String newEmail = sc.nextLine();
                        System.out.print("변경할 연락처 > "); String newPhone = sc.nextLine();

                        if (!targetEmail.equals(newEmail) && manager.existsEmail(newEmail)) {
                            System.out.println("이미 존재하는 이메일입니다. 수정을 취소합니다.");
                        } else {
                            manager.update(targetEmail, newName, newEmail, newPhone);
                            System.out.println("수정 및 파일 동기화가 완료되었습니다.");
                        }
                    }
                    break;

                case 6:
                    System.out.print("삭제할 회원의 이메일 입력 > ");
                    String deleteEmail = sc.nextLine();
                    if (manager.delete(deleteEmail)) System.out.println("삭제 및 파일 갱신이 완료되었습니다.");
                    else System.out.println("해당 회원이 존재하지 않습니다.");
                    break;

                case 7:
                    System.out.println("[정렬 기준을 골라주세요] [1]이름 가나다순 [2]등급이 높은 순");
                    int sortChoice = Integer.parseInt(sc.nextLine());
                    if (sortChoice == 1) {
                        manager.sortByName();
                        System.out.println("이름순 정렬이 완료되었습니다. [4]전체조회로 확인하세요.");
                    } else if (sortChoice == 2) {
                        manager.sortByGrade();
                        System.out.println("등급 높은 순 정렬이 완료되었습니다. [4]전체조회로 확인하세요.");
                    } else {
                        System.out.println("잘못된 번호입니다.");
                    }
                    break;

                case 8:
                    System.out.println("이용해주셔서 감사합니다.");
                    sc.close();
                    return;

                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }
}