package org.example.springtheory.ch03.ex_3_2.dao;

import org.example.springtheory.ch03.ex_3_2.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// * 문제점
// UserDAO의 다른 모든 곳에서는 인터페이스를 이용하게 만들어서
// DB커넥션을 제공하는 클래스에 대한 구체적인 정보는 모두 제거했지만,
// 초기에 한 번 어떤 클래스의 오브젝트를 사용할지를 결정하는 모든 생성자의 코드는 제거되지 않고 남아 있다. > 생성자 주입?
// 여전히 UserDAO 소스코드를 함께 제공해서,
// 필요할 때마다  UserDAO의 생성자 메서드를 직접 수정하라고 하지 않고는
// 고객에게 자유로운 DB커넥션 확장 기능을 가진 UserDAO를 제공할 수 없다.

// "관계설정 책임의 분리"


// * 직접 만든 싱글톤(자바 코드로 구현하는 전통적인 싱글톤 패턴 - GoF의 디자인 패턴)
// 만드는 순서
//  1) 클래스당 오브젝트를 1개만 담아둘 자기 자신 타입의 static 필드를 만든다.
//  2) 생성자를 private으로 막아 외부에서 new 로 마구 만들지 못하게 한다.
//  3) 유일한 오브젝트를 돌려주는 static 메서드(getInstance)를 둔다.
//     - 처음 호출될 때 한 번만 만들고, 그 다음부터는 이미 만든 오브젝트를 그대로 돌려준다.

// * 직접 만든 싱글톤의 한계 -> 그래서 스프링은 '싱글톤 레지스트리'로 대신 관리해준다.
// - private 생성자라서 상속이 불가능하다(객체지향의 장점인 상속/다형성을 못 쓴다.)
// - 테스트가 어렵다(생성 방식이 고정되어 가짜(mock) 오브젝트로 갈아끼우기 힘들다.
// - getInstance처럼 의존 오브젝트(simpleConnectionMaker) 주입이 어색하다.(두 번째 호출부터는 넘긴 인자가 무시되는 경우가 생기기때문)
// - 클래스 로더가 여러 개인 환경에서는 싱글톤이 여러 개 생길 수 있다.
// - static 필드라 사실상 전역 상태가 되어 아무 데서나 접근 가능해진다.

public class UserDAO {

    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDAO(SimpleConnectionMaker simpleConnectionMaker) {
        this.simpleConnectionMaker = simpleConnectionMaker;
    }

    protected UserDAO() {}

    // 컨텍스트 : 변하지 않는 JDBC 작업의 공통 흐름(변하지 않는 틀)
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
        jdbcContextWithStatementStrategy( new UserDAOAdd(user) );
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy( new UserDAODeleteAll() );
    }
}