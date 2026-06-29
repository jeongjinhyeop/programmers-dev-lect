package org.example.springtheory.ch05.exception;

import java.sql.SQLException;

public class DataService {
    private final FileLogger logger;
    DataService(FileLogger logger) { this.logger = logger; }

    String fetchWithRetry(FlakyService flaky) {
        int maxRetry = 3;
        for (int attempt = 1; attempt <= maxRetry; attempt++) {
            try {
                String result = flaky.fetch();
                logger.log("INFO", attempt + "번째 시도 성공: " + result);
                return result;                       // 정상 흐름 복귀 = 복구
            } catch (SQLException e) {
                logger.log("WARN", attempt + "번째 시도 실패: " + e.getMessage());
            }
        }
        logger.log("ERROR", "재시도 " + maxRetry + "회 모두 실패");
        throw new RuntimeException("재시도 " + maxRetry + "회 모두 실패했습니다."); // 통보
    }

    void avoidByThrows(FlakyService f) throws SQLException {
        f.fetch();
    }

    // (b) 로그 등 부가 작업만 하고, 처리는 다시 던져 호출자에게 맡김
    void avoidByRethrow(FlakyService f) throws SQLException {
        try {
            f.fetch();
        } catch (SQLException e) {
            logger.log("WARN", "회피: 여기서 처리하지 않고 호출자에게 넘김 - " + e.getMessage());
            throw e;   // 원래 예외를 그대로 다시 던진다
        }
    }

    void registerUser(String id) {
        try {
            insertUser(id);
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {           // 무결성 제약 위반 = 중복
                logger.log("ERROR", "아이디 중복: " + id);
                throw new DuplicateUserIdException(id, e);    // 의미 부여 + 원인 보존
            }
            logger.log("ERROR", "회원 저장 중 DB 오류: " + id);
            throw new RuntimeException("회원 저장 중 DB 오류", e);
        }
    }

    void insertUser(String id) throws SQLException {
        throw new SQLException("Duplicate entry", "23000");  // 시연용
    }

    static class DuplicateUserIdException extends RuntimeException {
        DuplicateUserIdException(String id, Throwable cause) {
            super("이미 존재하는 아이디입니다: " + id, cause);   // cause로 원인 보존
        }
    }
}
