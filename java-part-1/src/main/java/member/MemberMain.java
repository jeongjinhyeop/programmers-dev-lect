package member;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberMain {
    static int totalCnt = 0;
    static int memberCnt = 0;
    static Scanner sc = new Scanner(System.in);
    private static final String DIR = "member";
    File folder = new File(DIR);
    private static final Pattern MEMBER_PATTERN = Pattern.compile("^이름 : (.+?) 이메일 : (.+?) 핸드폰 : (.+?)");
    private static final String PHONE_REGEX = "^01[016789]\\d{8}$"; // 01X로 시작하는 숫자 11자리
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+$"; // xxxx@xxxxx 형식


    public int printPricePlan(){
        Scanner sc = new Scanner(System.in);
        System.out.println("[요금제를 선택하세요]");
        System.out.println("[1]Lite : 10 명 [2]Basic : 20 명 [3]Premium : 30 명");

        return Integer.parseInt(sc.nextLine());
    }

    public int printMenu(int memberCnt) {
        Scanner sc = new Scanner(System.in);
        System.out.println("[수행할 업무를 선택하세요 - 현재 회원수 : " +memberCnt + "/" + totalCnt  + "]");
        System.out.println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)");
        System.out.println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제");
        System.out.println("[7]프로그램 종료");

        return Integer.parseInt(sc.nextLine());
    }

    public void addMember(List<Member> members) {
        String today = LocalDate.now().toString();
        File file = new File(DIR, today+ ".txt");
        StringBuilder sb = new StringBuilder();
        String name;
        String email;
        String phone;

        System.out.println("이름을 입력해주세요.");
        name = sc.nextLine();
        if (memberCnt == totalCnt) {
            System.out.println("회원이 꽉 찼습니다.");
            return;
        }

        while (true) {
            System.out.println("이메일을 입력해주세요.");
            email = sc.nextLine();
            if (checkEmail(members, email)) {
                System.out.println("이미 존재하는 메일입니다.");
                continue;
            }
            if (!Pattern.matches(EMAIL_REGEX, email)) {
                System.out.println("올바르지 않은 이메일 형식입니다. abcd@efgh 형식으로 입력해주세요.");
                continue;
            }
            break;
        }

        while(true) {
            System.out.println("핸드폰 번호를 입력해주세요.");
            phone = sc.nextLine();

            if (!Pattern.matches(PHONE_REGEX, phone)) {
                System.out.println("올바르지 않은 핸드폰 형식입니다. 숫자 01로 시작하여 총 11자리로 입력해주세요");
                continue;
            }
            break;
        }
        Member newMember = new Member(name, email, phone);
        members.add(newMember);
        sb.append("이름 : ").append(name).append(" ")
          .append("이메일 : ").append(email)
          .append(" ").append("핸드폰 : ").append(phone)
          .append("\n");
        memberCnt = members.size();

        try (FileWriter fw = new FileWriter(file, true);){
            fw.write(sb.toString());
            System.out.println(file.getName() + "에 저장 완료");
        } catch (IOException e) {
            System.out.println("저장 중 오류 : " + e.getMessage());
        }
    }

    public boolean checkEmail(List<Member> members, String email) {
        for (int i = 0; i < members.size(); i++) {
            if(email.equals(members.get(i).getEmail())) return true;
        }
        return false;
    }

    public void selectEmail(List<Member> members, String email){
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

    public void selectName(List<Member> members, String name){
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

    public void selectAll(List<Member> members){
        for (int i = 0; i < members.size(); i++) {   // memberCnt 까지만! (빈 칸 null 출력 방지)
            System.out.println("[이름] " + members.get(i).getName() +
                    ", [이메일] " + members.get(i).getEmail() +
                    ", [연락처] " + members.get(i).getPhone());
        }
        return;
    }
    public void updateMember(List<Member> members, String email){
        String today = LocalDate.now().toString();
        File file = new File(DIR, today+ ".txt");
        StringBuilder sb = new StringBuilder();
        int idx = -1;
        for (int i = 0; i < members.size(); i++) {
            if (email.equals(members.get(i).getEmail())) {
                idx = i;
                System.out.println("\n=== 회원 정보 수정 ===");

                System.out.println("변경할 이름을 입력해주세요");
                members.get(i).setName(sc.nextLine());

                System.out.println("변경할 메일을 입력해주세요");
                members.get(i).setEmail(sc.nextLine());
                //이렇게 작성하면 내가 이전에 쓰던 메일을 쓸 수 없게 되는 현상 발생
                if(checkEmail(members, email)){
                    System.out.println("이미 존재하는 메일입니다.");
                    return;
                }

                System.out.println("변경할 번호를 입력해주세요");
                members.get(i).setPhone(sc.nextLine());
                break;
            }
        }
        if (idx == -1) { System.out.println("찾으시는 회원이 없습니다."); return; }

        for (int i = 0; i < members.size(); i++) {
            sb.append("이름 : ").append(members.get(i).name).append(" ")
                    .append("이메일 : ").append(members.get(i).email)
                    .append(" ").append("핸드폰 : ").append(members.get(i).phone).append("\n");
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))){
            bw.write(sb.toString());
            System.out.println("수정이 완료되었습니다.");
        } catch (IOException e){
            System.out.println("파일 수정 중 오류 발생");
        }
    }

    public void deleteMember(List<Member> members, String email){
        String today = LocalDate.now().toString();
        File file = new File(DIR, today+ ".txt");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < members.size(); i++) {
            if(email.equals(members.get(i).getEmail())){
                members.remove(i);
                memberCnt = members.size();

                break;
            }else{
                System.out.println("해당 회원이 존재하지 않습니다.");
                break;
            }
        }
        for (int i = 0; i < members.size(); i++) {
            sb.append("이름 : ").append(members.get(i).name).append(" ")
              .append("이메일 : ").append(members.get(i).email)
              .append(" ").append("핸드폰 : ").append(members.get(i).phone).append("\n");
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))){
            bw.write(sb.toString());
            System.out.println("삭제가 완료되었습니다.");
        } catch (IOException e){
            System.out.println("파일 삭제 중 오류 발생");
        }
    }

    public List<Member> loadMembersFromFile() {
        List<Member> members = new ArrayList<>();
        String today = LocalDate.now().toString();
        File file = new File(DIR, today+ ".txt");

        if (!file.exists()) {
            return members;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 한 줄 읽어온 데이터가 우리가 지정한 형식과 맞는지 검사
                Matcher matcher = MEMBER_PATTERN.matcher(line.trim());

                if (matcher.matches()) {
                    // 정규식 괄호() 순서대로 값을 쏙쏙 뽑아옵니다.
                    String name = matcher.group(1);  // 첫 번째 (.+?) > 이름
                    String email = matcher.group(2); // 두 번째 (.+?) > 이메일
                    String phone = matcher.group(3); // 세 번째 (.+?) > 핸드폰 번호
                    members.add(new Member(name, email, phone));
                }
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류 : " + e.getMessage());
        }

        return members;
    }

    public static void main(String[] args) {
        File folder = new File(DIR);
        if(!folder.exists()) folder.mkdir();
        MemberMain mm = new MemberMain();
        List<Member> members = mm.loadMembersFromFile();
        int num = mm.printPricePlan();
        memberCnt = members.size();
        //파일에서 회원 정보 읽어서 리스트로 읽어오도록 변경
        totalCnt = num * 10;
        while(true){
            int choice = mm.printMenu(memberCnt);
            switch (choice){
                case 1 :
                    mm.addMember(members);
                    break;
                case 2 :
                    System.out.println("검색하실 이메일을 입력해주세요");
                    String email = sc.nextLine();
                    mm.selectEmail(members, email);
                    break;
                case 3 :
                    System.out.println("검색하실 이름을 입력해주세요");
                    String name = sc.nextLine();
                    mm.selectName(members, name);
                    break;
                case 4 :
                    mm.selectAll(members);
                    break;
                case 5 :
                    System.out.println("변경하실 정보의 이메일을 입력해주세요");
                    mm.updateMember(members, sc.nextLine());
                    break;
                case 6 :
                    System.out.println("삭제하실 이메일을 입력해주세요");
                    mm.deleteMember(members, sc.nextLine());
                    break;
                case 7 :
                    System.out.println("이용해주셔서 감사합니다."); return;
                default:
                    System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }

}
