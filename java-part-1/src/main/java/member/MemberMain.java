package member;

import java.util.Scanner;

public class MemberMain {
    static int totalCnt = 0;
    static int memberCnt = 0;
    static Scanner sc = new Scanner(System.in);

    public static int printPricePlan(){
        Scanner sc = new Scanner(System.in);
        System.out.println("[요금제를 선택하세요]");
        System.out.println("[1]Lite : 10 명 [2]Basic : 20 명 [3]Premium : 30 명");

        return sc.nextInt();
    }

    public static int printMenu(int memberCnt) {
        Scanner sc = new Scanner(System.in);
        System.out.println("[수행할 업무를 선택하세요 - 현재 회원수 : " + memberCnt + "/" + totalCnt  + "]");
        System.out.println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)");
        System.out.println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제");
        System.out.println("[7]프로그램 종료");

        return sc.nextInt();
    }

    public static void addMember(String[][] members) {
        System.out.println("이름을 입력해주세요.");
        String name = sc.nextLine();
        if(memberCnt == members.length){
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

        members[memberCnt][0] = name;
        members[memberCnt][1] = email;
        members[memberCnt][2] = phone;
        memberCnt++;
    }

    public static boolean checkEmail(String[][] members, String email) {
        for (int i = 0; i < members.length; i++) {
            if(email.equals(members[i][1])) return true;
        }
        return false;
    }

    public static void selectEmail(String[][] members, String email){
        for (int i = 0; i < members.length; i++) {
            if(email.equals(members[i][1])){
                System.out.println("[이름] " + members[i][0] +
                        ", [이메일] " + members[i][1] +
                        ", [연락처] " + members[i][2]);

                return;
            }
        }
        System.out.println("찾으시는 정보가 없습니다.");
    }

    public static void selectName(String[][] members, String name){
        for (int i = 0; i < members.length; i++) {
            if(name.equals(members[i][0])){
                System.out.println("[이름] " + members[i][0] +
                        ", [이메일] " + members[i][1] +
                        ", [연락처] " + members[i][2]);

                return;
            }
        }
        System.out.println("찾으시는 정보가 없습니다.");
    }

    public static void selectAll(String[][] members){
        for (int i = 0; i < memberCnt; i++) {   // memberCnt 까지만! (빈 칸 null 출력 방지)
            System.out.println("[이름] " + members[i][0] +
                    ", [이메일] " + members[i][1] +
                    ", [연락처] " + members[i][2]);
        }
        return;
    }
    public static void updateMember(String[][] members, String email){
        int idx = -1;
        for (int i = 0; i < members.length; i++) {
            if (email.equals(members[i][1])) {
                idx = i;
                System.out.println("이름을 입력해주세요.");
                members[i][0] = sc.nextLine();
                System.out.println("이메일을 입력해주세요.");
                members[i][1] = sc.nextLine();
                System.out.println("번호를 입력해주세요.");
                members[i][2] = sc.nextLine();
                break;
            }
        }
        if (idx == -1) { System.out.println("찾으시는 회원이 없습니다."); return; }
    }

    public static void deleteMember(String[][] members, String email){

        for (int i = 0; i < members.length; i++) {
            if(email.equals(members[i][1])){
                members[i][0] = members[i + 1][0];
                members[i][1] = members[i + 1][1];
                members[i][2] = members[i + 1][2];

                members[memberCnt][0] = null;
                members[memberCnt][1] = null;
                members[memberCnt][2] = null;
                break;
            }else{
                System.out.println("해당 회원이 존재하지 않습니다.");
                break;
            }
        }

    }

    public static void main(String[] args) {
        int num = printPricePlan();
        String[][] members = new String[num * 10][3];
        totalCnt = num * 10;
        while(true){
            int choice = printMenu(memberCnt);
            switch (choice){
                case 1 : addMember(members);
                case 2 :
                    System.out.println("검색하실 이메일을 입력해주세요");
                    String email = sc.nextLine();
                    selectEmail(members, email);
                case 3 :
                    System.out.println("검색하실 이름을 입력해주세요");
                    String name = sc.nextLine();
                    selectName(members, name);
                case 4 :
                    selectAll(members);
                case 5 :
                    System.out.println("변경하실 정보의 이메일을 입력해주세요");
                    updateMember(members, sc.nextLine());
                case 6 :
                    System.out.println("삭제하실 이메일을 입력해주세요");
                    deleteMember(members, sc.nextLine());
                case 7 :
                    System.out.println("이용해주셔서 감사합니다."); return;
                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }

}
