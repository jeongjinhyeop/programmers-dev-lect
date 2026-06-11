package member;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class AbstractMemberMain {
    private static final String DIR = "member";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File folder = new File(DIR);
        if(!folder.exists()) folder.mkdir();

        AbstractMemberManager mm = new AbstractMemberManager(sc, DIR);
//        List<Member> members = mm.loadMembersFromFile();
        int num = mm.printPricePlan();
        mm.memberCnt = mm.members.size();
        //파일에서 회원 정보 읽어서 리스트로 읽어오도록 변경
        mm.totalCnt = num * 10;
        while(true){
            int choice = mm.printMenu(mm.memberCnt);
            switch (choice){
                case 1 :
                    mm.addMember(mm.members);
                    break;
                case 2 :
                    System.out.println("검색하실 이메일을 입력해주세요");
                    String email = sc.nextLine();
                    mm.selectEmail(mm.members, email);
                    break;
                case 3 :
                    System.out.println("검색하실 이름을 입력해주세요");
                    String name = sc.nextLine();
                    mm.selectName(mm.members, name);
                    break;
                case 4 :
                    mm.selectAll(mm.members);
                    break;
                case 5 :
                    System.out.println("변경하실 정보의 이메일을 입력해주세요");
                    mm.updateMember(mm.members, sc.nextLine());
                    break;
                case 6 :
                    System.out.println("삭제하실 이메일을 입력해주세요");
                    mm.deleteMember(mm.members, sc.nextLine());
                    break;
                case 7 :
                    System.out.println("이용해주셔서 감사합니다."); return;
                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }
}
