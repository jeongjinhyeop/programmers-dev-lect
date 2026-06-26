package org.example.springtheory.ch03.ex_3_3.dao;

import org.example.springtheory.ch03.ex_3_3.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
전략 패턴 리팩토링
클래스 폭발: 새로운 SQL 기능(수정, 삭제, 특정 조회 등)이 필요할 때마다 UserDAOAdd, UserDAODelete, UserDAOUpdate 등
 매번 새로운 서브클래스를 파일로 만들거나 익명 내부 클래스를 써야 해서 코드가 복잡해집니다.
상속의 한계: 자바는 단일 상속만 지원하는데, DAO 로직 하나 분리하겠다고 상속 구조를 써버리는 것은 결합도가 너무 높아 비효율적입니다.*
* */
// * 1. 로컬 클래스
public class UserDAO {

    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDAO(SimpleConnectionMaker simpleConnectionMaker) {
        this.simpleConnectionMaker = simpleConnectionMaker;
    }

    protected UserDAO() {}

    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        try (
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = statementStrategy.makeStatement(conn); // 변하는 부분을 전략에 위임
        ) {
            pstmt.executeUpdate();
        }
    }

    public void add(User user) throws ClassNotFoundException, SQLException {

            class UserDAOAdd implements StatementStrategy {

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
        }

        jdbcContextWithStatementStrategy( new UserDAOAdd() );
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {

        class UserDAODeleteAll implements StatementStrategy {

            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("DELETE FROM users");
            }
        }

        jdbcContextWithStatementStrategy( new UserDAODeleteAll() );
    }
}