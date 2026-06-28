package org.example.springtheory.strategyPattren;

public class DeleteAllStrategy implements StatementStrategy{

    public void run(Database db) {
        db.getUsers().clear();
        System.out.println("  [전략-별도클래스] 전체 삭제");
    }
}
