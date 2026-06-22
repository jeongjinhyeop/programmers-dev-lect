import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Member {
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

    public static void main(String[] args) {
        Member member = new Member();
        member.connection();
    }
}
