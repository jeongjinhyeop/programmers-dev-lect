package org.example.springtheory.strategyPattren;

public class AddStrategy implements StatementStrategy {
    // AddStrategy가 user를 생성자로 받아 필드에 저장하는 점을 눈여겨보세요.
    // 별도 클래스라 바깥 변수를 직접 못 쓰기 때문이에요. 이 번거로움을 Step 3에서 없애요.

    //step 3 에서 user 값을 밖에서 익명 클래스로 세팅이 가능해지기 때문에 해당 클래스가 필요 없어진다.
    private final User user;
    AddStrategy(User user){
        this.user = user;
    }

    public void run(Database db) {
        db.getUsers().add(user);
        System.out.println("  [전략-별도클래스] 추가: " + user.getName());
    }
}

// 익명 클래스로 전환 후 해당 클래스가 필요 없어짐

