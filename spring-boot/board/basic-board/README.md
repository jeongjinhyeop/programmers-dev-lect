## Spring Security & JWT 기반 인가 및 예외 처리 흐름

클라이언트가 게시글 상세 페이지에 접근하여 비동기 API(`/api/boards/{id}/with-comments`)로 상세 데이터를 요청할 때의 전체적인 시큐리티 및 예외 처리 흐름입니다.

```text
[클라이언트 (JS)]
       │
       │  1. GET /api/boards/{id}/with-comments
       │     Header: Bearer {JWT Token}
       ▼
[TokenAuthenticationFilter]
       │
       │  2. JWT 토큰 검증 및 Authentication 객체 저장
       ▼
[BoardApiController]
       │
       │  3. @AuthenticationPrincipal CustomUserDetails 주입
       ▼
[BoardService]
       │
       │  4. DB 게시글 조회 & 인가 조건 검사
       │     - 관리자(ROLE_ADMIN)인가?
       │     - 게시글 작성자(userId) == 로그인 유저인가?
       │
       ├───────────────────────────────┐
 [조건 충족 (통과)]             [조건 불충족 (권한 없음)]
       │                               │
       ▼                               ▼
 [Board 엔티티 반환]             [AccessDeniedException 발생]
       │                               │
       ▼                               ▼
 [BoardApiController]           [GlobalExceptionHandler]
       │                               │
       ▼                               ▼
 [200 OK 응답]                  [403 Forbidden 응답 (JSON)]
       │                               │
       └───────────────┬───────────────┘
                       │
                       ▼
               [클라이언트 (JS)]
                       │
  ┌────────────────────┴────────────────────┐
  │                                         │
[200 OK]                                 [403 Forbidden]
 - 데이터를 UI에 렌더링                    - "본인의 게시글만 조회할 수 있습니다" 알럿
 - `#board-detail` 노출         - 게시글 목록(/)으로 리다이렉트