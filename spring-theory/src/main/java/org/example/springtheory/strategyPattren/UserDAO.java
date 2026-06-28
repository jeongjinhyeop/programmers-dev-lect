package org.example.springtheory.strategyPattren;

public class UserDAO{
    private Database db;
    UserDAO(Database db) {
        this.db = db;
    }

    void context(StatementStrategy statementStrategy){
        db.open();
        statementStrategy.run(db);
        db.close();
    }
// 익명 클래스로 바꾼 근거
    //1. 클래스 파일의 낭비
    //2. 유저 객체 전달의 번거로움

// 고칠점
    //1. 유저 객체는 외부에서 전달하는데 그 외부라함은 데이터를 사용하는 클라이언트임 팩토리가 아니다.
    // DTO 나 VO는 UserDAO, UserService와 같은 행위 객체가 아닌 일반 객체로써 컴포넌트 스캔의 대상이 아님
    // 따라서 빈으로 등록되는 객체가 아니다.
//    void deleteAll() { context(new DeleteAllStrategy()); }
//    > 위의 코드를 익명 클래스로 리팩토링
/*void deleteAll() {
    StatementStrategy strategy = new StatementStrategy() {
        @Override
        public void run(Database db) {
            db.getUsers().clear();
            System.out.println("  [전략-익명] 전체 삭제");
        }
    };
    context(strategy);
}*/

// 람다를 사용하여 코드 리팩토링
    void deleteAll() {
        context(db -> {
            db.getUsers().clear();
            System.out.println("  [전략-람다] 전체 삭제");
        });
    }

//    void add(User user) { context(new AddStrategy(user)); }
//    > 위의 코드를 익명 클래스로 리팩토링
    /*    void add(User user){
        StatementStrategy statementStrategy = new StatementStrategy() {
            @Override
            public void run(Database db) {
                db.getUsers().add(user);                 // ← 바깥의 user를 그대로 가져올 올 수 있다.
                System.out.println("  [전략-익명] 추가: " + user.getName());
            }
        };
        context(statementStrategy);
    }*/

    // 람다를 사용하여 코드 리팩토링
    void add(User user) {
        context(db -> {
            db.getUsers().add(user);                     // user는 여전히 캡처
            System.out.println("  [전략-람다] 추가: " + user.getName());
        });
    }

    //어느 스타일을 쓰든 "연결 열기 → 전략 → 연결 닫기" 흐름은 동일하게 나온다.
    public static void main(String[] args) {
        Database db = new Database();
        UserDAO dao = new UserDAO(db);

        dao.deleteAll();
        dao.add(new User("u1", "김"));
        dao.add(new User("u2", "이"));

        System.out.println("\n현재 사용자 수: " + db.getUsers().size());
        for (User u : db.getUsers()) System.out.println("사용자: " + u.getName());
    }
}


/*
*
* 1.컨텍스트(불변)와 전략(가변)이 각각 무엇을 맡는지 말할 수 있다
    컨텍스트는 바뀌지 않는 틀의 부분을 이른다. 예를들어 텍스트 메서드가 그러하다
    전략부분은 add, deleteAll 과 같이 가변의 데이터가 들어가는 부분을 이른다.
 * 2.컨텍스트가 "인터페이스에만 의존"한다는 게 왜 OCP인지 안다
    OCP는 외부의 수정이 발생하지 않고도 기능이 잘 돌아가야한다.
    add, deleteAll만 변경하고 컨텍스트 수정하지 않았음에도 코드가 잘 실행되는 것으로 보아 OCP를 잘 지켰다.
 * 3.별도 클래스 전략이 user를 생성자로 받아야 했던 이유를 안다
    user 를 생성자로 받아야 했던 이유는 외부에서 추가할 값인 유저의 데이터를 동적으로 넣어줘야 값을 세팅할 수 있기 때문이다.
    이를 위해 유저를 파라메터로 넣어주면 addStrategy에서는 user를 동적을 받기 위해 별도의 생성자 주입이 필요했다.
    하지만 클래스 파일을 따로 생성하는 것이아니라 익명클래스를 이용함으로써 대입받을 파라메터를
    부모 클래스에서 받아다가 사용하기 때문에 별도로 필요한 클래스 파일이 필요없어지게 된다.
    즉 삭제할 클래스파일 내의 유저 객체를 별도로 생성하여 파라메터로 받을 필요가 없어지게 된 것
 * 4.익명 클래스가 지역 변수를 캡처해 생성자가 필요 없어지는 걸 설명할 수 있다
    메서드의 파라메터로 유저를 받아서 사용한다 따라서 생성자가 필요 없다.

 * 5.함수형 인터페이스라 람다로 줄일 수 있는 이유를 안다
   * 구현 안 하고 선언만 해놓은 메서드가 딱 1개만 있는 인터페이스를
   * **'함수형 인터페이스'**라고 부르고, 이는 **'익명 클래스'**나 **'람다식'**으로 구현해서 쓸 수 있다."
   * 생략해도 컴퓨터가 다 알아들을 수 있기 때문입니다.
     람다(Lambda)로 바꿀 수 있는 절대적인 조건은 "인터페이스 내에 추상 메서드가 딱 1개만 존재할 것(함수형 인터페이스)"입니다.
     이 조건이 만족되면 자바 컴파일러는 엄청난 생략을 허용해 줍니다.
     *
     * 예시 코드
     StatementStrategy strategy = new StatementStrategy() {
        @Override
        public void run(Database db) {
            db.getUsers().clear();
        }
        };
    1. new StatementStrategy()? 어차피 좌변에 타입이 적혀있으니 생략 가능!
    2. 어차피 이 인터페이스에는 메서드가 run() 딱 하나뿐이네? 다른 메서드랑 헷갈릴 일이 없으니 @Override public void run도 생략 가능!
    3. 매개변수 Database db에서 타입(Database)도 어차피 run 메서드 스펙에 적혀있으니 생략 가능!
    *
    결론: 구현해야 할 메서드가 딱 하나(run)밖에 없기 때문에, 거추장스러운 클래스 이름, 메서드 이름을 전부 지워버리고
         알맹이(파라미터와 실행 로직)만 남겨도 컴파일러가 알아들을 수 있어서 람다로 줄일 수 있는 것이다.
* */