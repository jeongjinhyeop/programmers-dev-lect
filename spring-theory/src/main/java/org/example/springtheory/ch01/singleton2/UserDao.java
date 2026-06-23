package org.example.springtheory.ch01.singleton2;

public class UserDao {
    private static final UserDao instance = new UserDao();
    private UserDao() {}
    static UserDao getInstance() { return instance; }

    private ConnectionMaker connectionMaker = SimpleConnectionMaker.getInstance(); // ✅ 필드 OK

    String findUser(String userId) {            // ✅ 요청별 데이터는 파라미터
        return userId + " 조회 [" + connectionMaker.makeConnection() + "]";
    }
}
