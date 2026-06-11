package abstractMember;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberManager {
    private final List<Member> members;
    private final int capacity;
    private final File file;

    private final Pattern MEMBER_PATTERN = Pattern.compile("^등급:(.+?),이름:(.+?),이메il:(.+?),연락처:(.+?)$");

    public MemberManager(int capacity) {
        this.capacity = capacity;
        this.members = new ArrayList<>();
        this.file = new File("members.txt");
        loadFromFile();
    }

    public boolean isFull() { return members.size() >= capacity; }
    public int getCount()   { return members.size(); }
    public int getCapacity() { return capacity; }

    public boolean existsEmail(String email) {
        for (Member m : members) {
            if (m.getEmail().equals(email)) return true;
        }
        return false;
    }

    public void add(Member m) {
        members.add(m);
        saveToFile();
    }

    public Member findByEmail(String email) {
        for (Member m : members) {
            if (m.getEmail().equals(email)) return m;
        }
        return null;
    }

    public Member findByName(String name) {
        for (Member m : members) {
            if (m.getName().equals(name)) return m;
        }
        return null;
    }

    public void printAll() {
        for (Member m : members) {
            m.printInfo();
        }
    }

    public boolean update(String email, String name, String newEmail, String phone) {
        Member m = findByEmail(email);
        if (m == null) return false;
        m.update(name, newEmail, phone);
        saveToFile();
        return true;
    }

    public boolean delete(String email) {
        Member m = findByEmail(email);
        if (m == null) return false;

        members.remove(m);
        saveToFile();
        return true;
    }

    public void sortByName() {
        Collections.sort(members);
    }

    public void sortByGrade() {
        members.sort(new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                return Integer.compare(getGradePriority(m2.getGrade()), getGradePriority(m1.getGrade()));
            }
            private int getGradePriority(String grade) {
                if ("VIP".equals(grade)) return 2;
                return 1; // 일반
            }
        });
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Member m : members) {
                bw.write(m.toFileString());
            }
        } catch (IOException e) {
            System.out.println("[시스템 오류] 파일 저장에 실패했습니다: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = MEMBER_PATTERN.matcher(line.trim());
                if (matcher.matches()) {
                    String grade = matcher.group(1);
                    String name = matcher.group(2);
                    String email = matcher.group(3);
                    String phone = matcher.group(4);

                    // 다형성을 보장하며 복구할 구체 클래스 매핑
                    switch (grade) {
                        case "VIP":  members.add(new VipMember(name, email, phone)); break;
                        default:     members.add(new NormalMember(name, email, phone)); break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[시스템 오류] 데이터를 파일에서 불러오는 중 오류가 발생했습니다.");
        }
    }
}