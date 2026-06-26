package org.example.springtheory.ch03.ex_3_3.dao;

import org.example.springtheory.ch03.ex_3_3.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
// * 2. 익명 내부 클래스
// 익명 클래스를 사용한 리팩토링
public class UserDAO_2 {

    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDAO_2(SimpleConnectionMaker simpleConnectionMaker) {
        this.simpleConnectionMaker = simpleConnectionMaker;
    }

    protected UserDAO_2() {}

    // 컨텍스트 : 변하지 않는 JDBC 작업의 공통 흐름
    //  - 커넥션을 얻고, 전달받은 '전략'에게 statement 생성을 맡기고, 실행하고, 자원을 정리한다.
    //  - 어떤 SQL을 실행할지는 전혀 모른다. 그건 strategy가 결정한다(인터페이스에만 의존).



    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        try (
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = statementStrategy.makeStatement(conn); // 변하는 부분을 전략에 위임
        ) {
            pstmt.executeUpdate();
        }
    }

    public void add(User user) throws ClassNotFoundException, SQLException {

        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO users(id, name, password) VALUES(?, ?, ?)"
                );

                pstmt.setString(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPassword());

                return pstmt;
            }
        };

        jdbcContextWithStatementStrategy( strategy );
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {

        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("DELETE FROM users");
            }
        };

        jdbcContextWithStatementStrategy( strategy );
    }
}