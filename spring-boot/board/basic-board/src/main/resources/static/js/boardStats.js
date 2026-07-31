$(document).ready(() => {
    // 1. 토큰 존재 여부 확인
    if (!checkToken()) return;

    loadStats(); // 처음엔 전체(1편 이상) 작성자를 보여준다

    // 적용 버튼 - 입력한 최소 게시글 수로 다시 조회한다
    $('#applyBtn').on('click', () => loadStats());

    // 숫자 입력 중 Enter 키로도 적용되게 한다
    $('#minCount').on('keydown', (e) => {
        if (e.key === 'Enter') loadStats();
    });
});

// 💡 JWT 토큰 존재 여부 및 세션 확인
let checkToken = () => {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 필요한 서비스입니다.');
        window.location.href = "/users/login";
        return false;
    }
    return true;
};

// 통계 데이터를 로드하는 함수 - minCount 는 서버 쿼리의 having 조건이 된다
let loadStats = () => {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 만료되었습니다. 다시 로그인해 주세요.');
        window.location.href = '/users/login';
        return;
    }

    // 입력값이 비었거나 1 미만이면 1로 보정한다 (음수/0 을 보내는 실수 방지)
    let minCount = parseInt($('#minCount').val());
    if (isNaN(minCount) || minCount < 1) {
        minCount = 1;
        $('#minCount').val(1);
    }

    $.ajax({
        type: 'GET',
        url: '/api/boards/stats/authors',
        // 💡 [핵심] Authorization 헤더에 Access Token 탑재
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: {
            minCount: minCount
        },
        success: (response) => {
            renderStats(response);
        },
        error: (xhr) => {
            console.error('오류 발생:', xhr);

            // 💡 401(미인증) 또는 403(권한 없음) 발생 시 처리
            if (xhr.status === 401 || xhr.status === 403) {
                alert('로그인이 만료되었거나 권한이 없습니다. 다시 로그인해 주세요.');
                localStorage.removeItem('accessToken');
                window.location.href = '/users/login';
            } else {
                alert('통계 데이터를 불러오는 중 오류가 발생했습니다.');
            }
        }
    });
};

// 통계 목록을 테이블에 그린다
let renderStats = (stats) => {
    const $content = $('#statsContent');
    $content.empty(); // 기존 내용 비우기

    if (stats == null || stats.length <= 0) {
        $content.append(
            `<tr>
                <td colspan="3" style="text-align: center;">조건에 맞는 작성자가 없습니다.</td>
            </tr>`
        );
        return;
    }

    stats.forEach((item, index) => {
        const rank = index + 1;
        const author = item.userName ? `${item.userName} (${item.userId})` : item.userId;

        const rankBadge = rank <= 3
            ? `<span class="rank-badge rank-${rank}">${rank}</span>`
            : rank;

        $content.append(
            `
            <tr>
                <td>${rankBadge}</td>
                <td>${author}</td>
                <td><span class="board-count">${item.boardCount}</span></td>
            </tr>
            `
        );
    });
};