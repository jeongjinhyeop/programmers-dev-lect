package org.example.springtheory.ch01.ex_1_6.dao;

import org.example.springtheory.ch01.ex_1_6.domain.User;

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

    // 1) 클래스가 자기 자신의 유일한 오브젝트를 보관 (클래스당 1개)
    private static UserDAO instance;

    private SimpleConnectionMaker simpleConnectionMaker;

    // 2) 생성자를 private 으로 막는다 -> 이제 외부에서 new UserDAO(...) 불가
    private UserDAO(SimpleConnectionMaker simpleConnectionMaker) {
        this.simpleConnectionMaker = simpleConnectionMaker;
    }

    // 3) 유일한 오브젝트를 돌려주는 통로
    //    - synchronized: 여러 스레드가 동시에 들어와 오브젝트가 2개 만들어지는 것을 막는다.
    public static synchronized UserDAO getInstance(SimpleConnectionMaker simpleConnectionMaker) {
        if (instance == null) {
            instance = new UserDAO(simpleConnectionMaker);
        }

        return instance;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {

        String query = "INSERT INTO users (id, name, password) VALUES (?, ?, ?)";

        try (
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            pstmt.executeUpdate();
        }

    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM users WHERE id = ?";

        try (
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setString(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            resultSet.next();

            User user = new User();
            user.setId( resultSet.getString("id") );
            user.setName( resultSet.getString("name") );
            user.setPassword( resultSet.getString("password") );

            return user;
        }

    }


    // UserDAO의 소스코드를 제공하면, getConnection() 메서드를 원하는 방식으로 확장한후
    // UserDAO의 기능과 함께 사용할 수 있다.
    // 기존에는 같은 클래스에 다른 메서드로 분리됐던 DB 커넥션 연결이라는 관심을
    // 이번에는 상속을 통해 서브클래스로 분리해버리는 것이다.
}