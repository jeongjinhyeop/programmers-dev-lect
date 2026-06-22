package member;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {
    private final List<Member> members = new ArrayList<>();
//    private final int capacity;
    private PricePlan pricePlan;
    private static final String FILE = "members.txt";

    public  MemberManager (PricePlan pricePlan) {
        this.pricePlan = pricePlan;
    }

    public void add(Member m) {
        String sql = "INSERT INTO MEMBER (GRADE, NAME, EMAIL, PHONE) VALUES (?, ?, ?, ?);";
        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, m.getGrade());
            pstmt.setString(2, m.getName());
            pstmt.setString(3, m.getEmail());
            pstmt.setString(4, m.getPhone());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private Member toMember(ResultSet rs) throws SQLException {
        String grade = rs.getString("grade");
        String name  = rs.getString("name");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        return grade.equals("VIP")
                ? new VipMember(name, email, phone)
                : new NormalMember(name, email, phone);
    }

    public Member findByEmail(String email) {
        String sql = "SELECT grade, name, email, phone FROM member WHERE email = ?";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return toMember(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;   // 없으면 null
    }

    public Member findByName(String name) {
        String sql = "SELECT grade, name, email, phone FROM member WHERE name = ?";
       try (Connection conn = connection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
           ps.setString(1, name);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return null;
    }

    public void printAll() {
        String sql = "SELECT grade, name, email, phone FROM member";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            boolean empty = true;
            while (rs.next()) { toMember(rs).printInfo(); empty = false; }
            if (empty) System.out.println("등록된 회원이 없습니다.");
        } catch (SQLException e) { throw new RuntimeException(e); }
    }


    public boolean existsEmail(String email) {
        String sql = "SELECT COUNT(*) FROM member WHERE email = ?";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return false;
    }

    public int size() {
        String sql = "SELECT COUNT(*) FROM member";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { throw new RuntimeException(e); }
        return 0;
    }

    public boolean isFull() {
        return size() >= pricePlan.getCapacity();
    }

    public boolean update(String email, String name, String newEmail, String phone) {
        String sql = "UPDATE member SET name = ?, email = ?, phone = ? WHERE email = ?";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, newEmail);
            ps.setString(3, phone);
            ps.setString(4, email);          // WHERE는 기존 이메일
            return ps.executeUpdate() > 0;   // 바뀐 행이 있으면 성공
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public boolean delete(String email) {
        String sql = "DELETE FROM member WHERE email = ?";
        try (Connection conn = connection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public Connection connection(){
        String url = "jdbc:mysql://localhost:3306/member";
        String id = "root";
        String pw = "1q2w3e4r5t^^";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, id, pw);

            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
