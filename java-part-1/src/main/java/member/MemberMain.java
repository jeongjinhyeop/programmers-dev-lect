package member;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberMain {
    static int totalCnt = 0;
    static int memberCnt = 0;
    static Scanner sc = new Scanner(System.in);

    public static int printPricePlan(){
        Scanner sc = new Scanner(System.in);
        System.out.println("[요금제를 선택하세요]");
        System.out.println("[1]Lite : 10 명 [2]Basic : 20 명 [3]Premium : 30 명");

        return Integer.parseInt(sc.nextLine());
    }

    public static int printMenu(int memberCnt) {
        Scanner sc = new Scanner(System.in);
        System.out.println("[수행할 업무를 선택하세요 - 현재 회원수 : " + memberCnt + "/" + totalCnt  + "]");
        System.out.println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)");
        System.out.println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제");
        System.out.println("[7]프로그램 종료");

        return Integer.parseInt(sc.nextLine());
    }

    public static void addMember(List<Member> members) {
        System.out.println("이름을 입력해주세요.");
        String name = sc.nextLine();
        System.out.println("memberCOunt: " + memberCnt);
        if(memberCnt == totalCnt){
            System.out.println("회원이 꽉 찼습니다.");
            return;
        }

        System.out.println("이메일을 입력해주세요.");
        String email = sc.nextLine();
        if(checkEmail(members, email)){
            System.out.println("이미 존재하는 메일입니다.");
            return;
        }

        System.out.println("핸드폰 번호를 입력해주세요.");
        String phone = sc.nextLine();

        Member newMember = new Member(name, email, phone);
        members.add(newMember);

        /*members[memberCnt][1] = email;
        members[memberCnt][2] = phone;
        memberCnt++;*/
        memberCnt = members.size();
    }

    public static boolean checkEmail(List<Member> members, String email) {
        for (int i = 0; i < members.size(); i++) {
            if(email.equals(members.get(i).getEmail())) return true;
        }
        return false;
    }

    public static void selectEmail(List<Member> members, String email){
        for (int i = 0; i < members.size(); i++) {
            if(email.equals(members.get(i).getEmail())){
                System.out.println("[이름] " + members.get(i).getName() +
                        ", [이메일] " + members.get(i).getEmail() +
                        ", [연락처] " + members.get(i).getPhone());

                return;
            }
        }
        System.out.println("찾으시는 정보가 없습니다.");
    }

    public static void selectName(List<Member> members, String name){
        boolean flag = false;
        for (int i = 0; i < members.size(); i++) {
            if(name.equals(members.get(i).getName())){
                flag = true;
                System.out.println("[이름] " + members.get(i).getName() +
                        ", [이메일] " + members.get(i).getEmail() +
                        ", [연락처] " + members.get(i).getPhone());

            }
        }

        if(!flag) System.out.println("찾으시는 정보가 없습니다.");
    }

    public static void selectAll(List<Member> members){
        for (int i = 0; i < memberCnt; i++) {   // memberCnt 까지만! (빈 칸 null 출력 방지)
            System.out.println("[이름] " + members.get(i).getName() +
                    ", [이메일] " + members.get(i).getEmail() +
                    ", [연락처] " + members.get(i).getPhone());
        }
        return;
    }
    public static void updateMember(List<Member> members, String email){
        int idx = -1;
        for (int i = 0; i < members.size(); i++) {
            if (email.equals(members.get(i).getEmail())) {
                idx = i;
                System.out.println("변경할 이름을 입력해주세요.");
                members.get(i).setName(sc.nextLine());
                System.out.println("변경할 이메일을 입력해주세요.");
                members.get(i).setEmail(sc.nextLine());
                System.out.println("변경할 번호를 입력해주세요.");
                members.get(i).setPhone(sc.nextLine());
                break;
            }
        }
        if (idx == -1) { System.out.println("찾으시는 회원이 없습니다."); return; }
    }

    public static void deleteMember(List<Member> members, String email){

        for (int i = 0; i < members.size(); i++) {
            if(email.equals(members.get(i).getEmail())){
                members.remove(i);

               /* members[i][0] = members[i + 1][0];
                members[i][1] = members[i + 1][1];
                members[i][2] = members[i + 1][2];*/


                /*members[memberCnt][0] = null;
                members[memberCnt][1] = null;
                members[memberCnt][2] = null;
                //회원 삭제 시 맴버수도 하나 감소시켜야 한다.
                memberCnt--;
                */
                memberCnt = members.size();
                break;
            }else{
                System.out.println("해당 회원이 존재하지 않습니다.");
                break;
            }
        }

    }

    public static void main(String[] args) {
        int num = printPricePlan();
        List<Member> members = new ArrayList<>();
        totalCnt = num * 10;
        while(true){
            int choice = printMenu(memberCnt);
            switch (choice){
                case 1 :
                    addMember(members);
                    break;
                case 2 :
                    System.out.println("검색하실 이메일을 입력해주세요");
                    String email = sc.nextLine();
                    selectEmail(members, email);
                    break;
                case 3 :
                    System.out.println("검색하실 이름을 입력해주세요");
                    String name = sc.nextLine();
                    selectName(members, name);
                    break;
                case 4 :
                    selectAll(members);
                    break;
                case 5 :
                    System.out.println("변경하실 정보의 이메일을 입력해주세요");
                    updateMember(members, sc.nextLine());
                    break;
                case 6 :
                    System.out.println("삭제하실 이메일을 입력해주세요");
                    deleteMember(members, sc.nextLine());
                    break;
                case 7 :
                    System.out.println("이용해주셔서 감사합니다."); return;
                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }

}
