package org.example.springtheory.strategyPattren;

public interface StatementStrategy {
    void run(Database db); //변하는 부분
}
