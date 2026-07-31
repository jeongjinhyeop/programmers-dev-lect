$(document).ready(() => {
    // 1. JWT 토큰 존재 여부 확인
    if (!checkToken()) return;

    loadBoard(1); // 처음엔 1페이지를 보여준다

    // [추가 - QueryDSL] 검색 버튼 - 입력한 조건으로 1페이지부터 다시 조회한다
    $('#searchBtn').on('click', () => loadBoard(1));

    // [추가 - QueryDSL] 초기화 버튼 - 조건을 비우고 전체 목록(1페이지)으로 돌아간다
    $('#searchResetBtn').on('click', () => {
        $('#searchTitle').val('');
        $('#searchUserId').val('');
        $('#searchFrom').val('');
        $('#searchTo').val('');
        loadBoard(1);
    });

    // [추가 - QueryDSL] 검색어 입력 중 Enter 키로도 검색되게 한다
    $('#searchTitle, #searchUserId').on('keydown', (e) => {
        if (e.key === 'Enter') loadBoard(1);
    });
});

const PAGE_SIZE = 10; // 한 페이지에 보여줄 게시글 수

// 💡 [수정] LocalStorage의 JWT 토큰을 검증하여 없으면 로그인 페이지로 보냄
const checkToken = () => {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 필요한 서비스입니다.');
        window.location.href = '/users/login';
        return false;
    }
    return true;
}

// 검색 폼의 입력값을 모아 "값이 있는 것만" 객체로 만든다
const getSearchCondition = () => {
    const condition = {};
    const title = $('#searchTitle').val();
    const userId = $('#searchUserId').val();
    const from = $('#searchFrom').val();
    const to = $('#searchTo').val();

    if (title) condition.title = title;
    if (userId) condition.userId = userId;
    if (from) condition.from = from;
    if (to) condition.to = to;
    return condition;
}

// 특정 페이지의 게시글 + 하단 페이지 번호를 로드하는 함수
const loadBoard = (page) => {
    const token = localStorage.getItem('accessToken');

    $.ajax({
        type: 'GET',
        url: '/api/boards/search',
        // 💡 [핵심 수정] JWT Access Token을 HTTP Header에 추가
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: {
            page: page,
            size: PAGE_SIZE,
            ...getSearchCondition()
        },
        success: (response) => {
            renderBoards(response.content);                // 게시글 목록 그리기
            renderPagination(page, response.totalPages);   // 하단 1,2,3... 페이지 번호 그리기
        },
        error: function (xhr) {
            console.error('오류 발생:', xhr);

            // 💡 [추가] 401(미인증) 또는 403(권한없음)인 경우 처리
            if (xhr.status === 401 || xhr.status === 403) {
                alert('로그인이 만료되었거나 권한이 없습니다. 다시 로그인해 주세요.');
                localStorage.removeItem('accessToken'); // 유효하지 않은 토큰 삭제
                window.location.href = '/users/login';
            } else {
                alert('게시판 데이터를 불러오는 중 오류가 발생했습니다.');
            }
        }
    });
}

// 게시글 목록을 테이블에 그린다
const renderBoards = (boards) => {
    const $content = $('#boardContent');
    $content.empty(); // 기존 게시글 내용 비우기

    if (boards == null || boards.length <= 0) {
        $content.append(
            `<tr>
                <td colspan="5" style="text-align: center;">글이 존재하지 않습니다.</td>
            </tr>`
        );
        return;
    }

    boards.forEach((item) => {
        const author = item.userName ? `${item.userName} (${item.userId})` : item.userId;
        const commentBadge = item.commentCount > 0
            ? `<span class="comment-count">${item.commentCount}</span>`
            : '-';

        $content.append(
            `
            <tr>
                <td>${item.id}</td>
                <td><a href="/detail?id=${item.id}">${item.title}</a></td>
                <td>${author}</td>
                <td>${commentBadge}</td>
                <td>${item.created}</td>
            </tr>
            `
        );
    });
}

// 하단에 페이지 번호(1,2,3...)를 그린다
const renderPagination = (currentPage, totalPages) => {
    const $pagination = $('#pagination');
    $pagination.empty(); // 기존 번호 버튼 비우기

    for (let p = 1; p <= totalPages; p++) {
        const $btn = $(`<button class="btn page-btn">${p}</button>`);

        if (p === currentPage) {
            $btn.addClass('active');
            $btn.prop('disabled', true);
        }

        $btn.on('click', () => loadBoard(p));
        $pagination.append($btn);
    }
}