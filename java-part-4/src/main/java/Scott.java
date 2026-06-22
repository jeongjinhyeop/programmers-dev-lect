import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Scott {
    public Connection connection(){
        String url  = "jdbc:mysql://localhost:3306/scott";
        String user = "root";
        String pw = "1q2w3e4r5t^^";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pw);
            System.out.println("Conn Success!");

            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
