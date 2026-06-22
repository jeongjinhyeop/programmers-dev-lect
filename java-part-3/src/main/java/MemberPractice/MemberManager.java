package memberFinal;

import java.nio.file.*;
import java.util.*;
import java.io.*;

public class MemberManager {
    private final List<Member> members = new ArrayList<>();
//    private final int capacity;
    private PricePlan pricePlan;
    private static final String FILE = "members.txt";

    public  MemberManager (PricePlan pricePlan) {
        this.pricePlan = pricePlan;
    }

    public boolean isFull(){
        return  members.size() >= pricePlan.getCapacity();
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
        return pricePlan.getCapacity();
    }

    public PricePlan getPricePlan(){
        return pricePlan;
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

//    public void save(PricePlan pricePlan) {
//        try (FileWriter fw = new FileWriter(FILE)) {   // true 없음 = 덮어쓰기
//            fw.write(pricePlan.name() + "\n");
//            for (Member m : members) {
//                fw.write(m.toFileString() + "\n");
//            }
//        } catch (IOException e) {
//            System.out.println("저장 오류: " + e.getMessage());
//        }
//    }

    public void save(){
        File originFile = new File(FILE);
        if (originFile.exists()) {
            try {
                Path source = originFile.toPath();
                Path target = Paths.get("members.bak"); // 백업 파일명 (.bak)

                // StandardCopyOption.REPLACE_EXISTING : 이미 이전 백업 파일이 있다면 덮어씁니다.
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("백업 파일 생성 실패: " + e.getMessage());
            }
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))){
            oos.writeObject(pricePlan);
            oos.writeObject(members);
        }catch (IOException e){
            System.out.println("저장 오류");
        }
    }


    /*public static void load(){
        File file = new File(FILE);
        if(!file.exists()){
            return;
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            PricePlan savedPricePlan = (PricePlan) ois.readObject();
            List<Member> savedMembers = (List<Member>) ois.readObject();
            this.members.clear();
            if(savedMembers != null){
                this.members.addAll(savedMembers);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("불러오기 오류");
        }
    }*/

    public static MemberManager load() {
        File file = new File(FILE);

        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(file))) {

            PricePlan savedPricePlan = (PricePlan) ois.readObject();
            List<Member> savedMembers = (List<Member>) ois.readObject();

            MemberManager manager = new MemberManager(savedPricePlan);
            manager.members.addAll(savedMembers);

            return manager;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("불러오기 오류");
            return null;
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
