$(document).ready(() => {
    // 1. 토큰 존재 여부 확인
    if (!checkToken()) return;

    loadBoardDetail();
});

// 💡 JWT 토큰 확인 함수
let checkToken = () => {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 필요한 서비스입니다.');
        window.location.href = "/users/login";
        return false;
    }
    return true;
};

// 수정 페이지로 이동
let editArticle = () => {
    let resourceId = $('#hiddenId').val();
    window.location.href = "/update/" + resourceId;
};

// 게시글 삭제 (DELETE /api/boards/{id})
let deleteArticle = () => {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 만료되었습니다.');
        window.location.href = '/users/login';
        return;
    }

    let resourceId = $('#hiddenId').val();
    let filePath = $('#hiddenFilePath').val();

    if (!confirm('정말로 이 게시글을 삭제하시겠습니까?')) return;

    $.ajax({
        type: 'DELETE',
        url: '/api/boards/' + resourceId,
        headers: {
            'Authorization': 'Bearer ' + token
        },
        data: JSON.stringify({ filePath: filePath }),
        contentType: 'application/json',
        success: (response) => {
            alert('리소스가 성공적으로 삭제되었습니다.');
            window.location.href = '/';
        },
        error: (xhr) => {
            console.error('Error:', xhr);
            if (xhr.status === 401) {
                alert('로그인이 만료되었습니다. 다시 로그인해 주세요.');
                window.location.href = '/users/login';
            } else if (xhr.status === 403) {
                alert('본인의 게시글만 삭제할 수 있습니다.');
            } else {
                alert('리소스 삭제 중 오류가 발생했습니다.');
            }
        }
    });
};

// 게시글 상세 정보 및 댓글 목록 로드 (GET /api/boards/{id}/with-comments)
let loadBoardDetail = () => {
    const token = localStorage.getItem('accessToken');
    let hId = $('#hiddenId').val();

    $.ajax({
        type: 'GET',
        url: '/api/boards/' + hId + '/with-comments',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: (response) => {
            // 💡 1. 정상적으로 조회 성공 시 데이터 채우기
            $('#title').text(response.title);
            $('#content').text(response.content);
            $('#userId').text(response.userId);
            $('#created').text(response.created);

            // 본인 확인 로직: 로그인 시 localStorage에 저장한 userId와 작성자 userId 비교
            const currentUserId = localStorage.getItem('userId');
            if (currentUserId !== response.userId) {
                $('#editBtn').prop('disabled', true).hide();
                $('#deleteBtn').prop('disabled', true).hide();
            } else {
                $('#editBtn').prop('disabled', false).show();
                $('#deleteBtn').prop('disabled', false).show();
            }

            // 파일 목록 영역 비우기
            $('#fileList').empty();

            if (response.filePath && response.filePath.length > 0) {
                let filePath = response.filePath;
                $('#hiddenFilePath').val(filePath);

                let normalized = filePath.replace(/\\/g, '/');
                let fileName = normalized.substring(normalized.lastIndexOf('/') + 1);
                let fileElement = `
                    <li>
                        <a href="/api/boards/file/download/${fileName}">${fileName}</a>
                    </li>`;
                $('#fileList').append(fileElement);
            } else {
                $('#fileList').append('<li>첨부된 파일이 없습니다.</li>');
            }

            // 댓글 목록 렌더링
            renderComments(response.comments);

            // 💡 2. 데이터 세팅 완료 후 전체 화면 컨테이너 노출 (기본 hidden 상태 해제)
            $('#board-detail-container').show();
        },
        error: function (xhr) {
            console.error('오류 발생:', xhr);

            // 💡 3. 403 (권한 거부)와 401 (토큰 만료)의 동작을 명확히 분리
            if (xhr.status === 403) {
                // 백엔드에서 보낸 예외 메시지 읽기 (없으면 기본 메시지)
                let message = xhr.responseJSON && xhr.responseJSON.message
                    ? xhr.responseJSON.message
                    : '본인의 게시글만 조회할 수 있습니다.';

                alert(message);
                window.location.href = '/'; // 👈 로그인 창이 아닌 게시글 목록으로 이동!
            } else if (xhr.status === 401) {
                alert('인증이 만료되었습니다. 다시 로그인해 주세요.');
                localStorage.removeItem('accessToken');
                window.location.href = '/users/login';
            } else {
                alert('상세 데이터를 불러오는데 오류가 발생했습니다.');
            }
        }
    });
};

// 댓글 목록을 그려주는 함수
let renderComments = (comments) => {
    const $list = $('#commentList');
    $list.empty();

    $('#commentCount').text(comments && comments.length > 0 ? comments.length : '');

    if (comments == null || comments.length <= 0) {
        $list.append('<li class="no-comment">아직 댓글이 없습니다. 첫 댓글을 남겨보세요!</li>');
        return;
    }

    comments.forEach((c) => {
        $list.append(
            `
            <li class="comment-item">
                <div class="comment-meta">
                    <strong>${c.userId}</strong>
                    <span class="comment-date">${c.created}</span>
                </div>
                <p class="comment-content">${c.content}</p>
            </li>
            `
        );
    });
};

// 댓글 등록 (POST /api/boards/{boardId}/comments)
let submitComment = () => {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 만료되었습니다.');
        window.location.href = '/users/login';
        return;
    }

    let hId = $('#hiddenId').val();
    let content = $('#commentContent').val();

    if (content == null || content.trim() === '') {
        alert('댓글 내용을 입력해주세요.');
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/api/boards/' + hId + '/comments',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        contentType: 'application/json',
        data: JSON.stringify({
            userId: localStorage.getItem('userId'),
            content: content
        }),
        success: () => {
            $('#commentContent').val('');   // 입력칸 비우기
            loadBoardDetail();              // 목록 재갱신
        },
        error: (xhr) => {
            console.error('오류 발생:', xhr);
            if (xhr.status === 401) {
                alert('로그인이 만료되었습니다. 다시 로그인해 주세요.');
                window.location.href = '/users/login';
            } else if (xhr.status === 403) {
                alert('댓글 작성 권한이 없습니다.');
            } else {
                alert('댓글 등록 중 오류가 발생했습니다.');
            }
        }
    });
};