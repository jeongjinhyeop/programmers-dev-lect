package memberFinal;

import java.util.*;
import java.io.*;

public class MemberManager {
    private final List<Member> members = new ArrayList<>();
    private final int capacity;
    private static final String FILE = "members.txt";

    public  MemberManager (int capacity) {
        this.capacity = capacity;
        load();
    }

    public boolean isFull(){
        return  members.size() >= capacity;
    }

    public boolean existsEmail(String email){
        for(Member m :members){
            if(m.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void add(Member m) {
        members.add(m);
//        save();             // 추가 후 저장
    }

    public int getSize() {
        return members.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public Member findByEmail(String email){
        for (Member m : members){
            if(m.getEmail().equals(email)){
                return m;
            }
        }
        return  null;
    }

    public Member findByName(String name) {
        for (Member m : members) if (m.getName().equals(name)) {
            return m;
        }

        return null;
    }

    public void printAll() {
        if (members.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }
        for (Member m : members) m.printInfo();   // 다형성! 등급별로 다르게 출력
    }

    public boolean update(String email, String name, String newEmail, String phone) {
        Member m = findByEmail(email);
        if (m == null) return false;
        m.update(name, newEmail, phone);
//        save();             // 수정 후 저장
        return true;
    }

    public boolean delete(String email) {
        Member m = findByEmail(email);
        if (m == null) return false;
        members.remove(m);
//        save();             // 삭제 후 저장
        return true;
    }

    public void save(PricePlan pricePlan) {
        try (FileWriter fw = new FileWriter(FILE)) {   // true 없음 = 덮어쓰기
            fw.write(pricePlan.name() + "\n");
            for (Member m : members) {
                fw.write(m.toFileString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("저장 오류: " + e.getMessage());
        }
    }

    public void load() {
        File file = new File(FILE);
        if (!file.exists()) return;   // 처음 실행이면 파일이 없음

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
//            String firstLine = br.readLine(); // 요금제 불러올것이라 생각한 부분
//            PricePlan savedPricePlan = null;
//            if (firstLine != null && !firstLine.isBlank()) {
//                savedPricePlan = PricePlan.valueOf(firstLine.trim());
//            }
            br.readLine();//첫 줄 건너뛰
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split(",");       // [등급, 이름, 이메일, 연락처]
                String grade = CsvUtil.deEscapeCsvField(p[0]);
                String name  = CsvUtil.deEscapeCsvField(p[1]);
                String email  = CsvUtil.deEscapeCsvField(p[2]);
                String phone  = CsvUtil.deEscapeCsvField(p[3]);;
//                String grade = p[0], name = p[1], email = p[2], phone = p[3];

                Member m = grade.equals("VIP")
                        ? new VipMember(name, email, phone)
                        : new NormalMember(name, email, phone);
                members.add(m);
            }
        } catch (IOException e) {
            System.out.println("불러오기 오류: " + e.getMessage());
        }
    }

    public static PricePlan peekPricePlan() {
        File file = new File(FILE);
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String firstLine = br.readLine();
            if (firstLine != null && !firstLine.isBlank()) {
                return PricePlan.valueOf(firstLine.trim());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
