package memberFinal;

import java.util.*;

public class MemberManager {
    private final List<Member> members = new ArrayList<>();
    private final int capacity;
    public  MemberManager (int capacity) {
        this.capacity = capacity;
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

    public void add(Member m){
        members.add(m);
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
        return true;
    }

    public boolean delete(String email) {
        Member m = findByEmail(email);
        if (m == null) return false;
        members.remove(m);    // 당기기·null 자동!
        return true;
    }
}
