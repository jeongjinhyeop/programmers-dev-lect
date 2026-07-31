let selectedFile = null; // 파일은 1개만 선택 가능

$(document).ready(() => {
    if (!checkToken()) return; // 1. 토큰 체크 먼저 수행
    saved();
    fileChaged();
});

// 💡 JWT 토큰 확인 함수
let checkToken = () => {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 필요한 서비스입니다.');
        window.location.href = '/users/login';
        return false;
    }
    return true;
};

let saved = () => {
    $('#submitBtn').on('click', (event) => {
        event.preventDefault();

        const token = localStorage.getItem('accessToken');
        if (!token) {
            alert('로그인이 만료되었습니다. 다시 로그인해 주세요.');
            window.location.href = '/users/login';
            return;
        }

        let formData = new FormData($('#writeForm')[0]);

        $.ajax({
            type: 'POST',
            url: '/api/boards',
            // 💡 [핵심] Authorization 헤더 추가 (FormData 송신 시에도 헤더 설정 가능)
            headers: {
                'Authorization': 'Bearer ' + token
            },
            data: formData,
            processData: false, // FormData 전송을 위해 false 유지
            contentType: false, // FormData 전송을 위해 false 유지
            success: function(response) {
                alert('게시글이 성공적으로 등록되었습니다!');
                window.location.href = '/';
            },
            error: function(xhr) {
                console.error('오류 발생:', xhr);

                // 💡 인증/권한 에러 예외 처리
                if (xhr.status === 401 || xhr.status === 403) {
                    alert('로그인이 만료되었거나 권한이 없습니다.');
                    localStorage.removeItem('accessToken');
                    window.location.href = '/users/login';
                } else {
                    let message = '게시글 등록 중 오류가 발생하였습니다.';
                    if (xhr.responseJSON && xhr.responseJSON.message) {
                        message = xhr.responseJSON.message;
                    }
                    alert(message);
                }
            }
        });
    });
};

let fileChaged = () => {
    // 파일 선택 시 이벤트
    $('#file').on('change', function(e) {
        const file = e.target.files[0]; // 첫 번째 파일만 선택
        selectedFile = file; // 선택된 파일을 변수에 저장
        updateFileList(); // 파일 목록 업데이트
    });
};

// 파일 목록 업데이트 함수 (파일 하나만)
let updateFileList = () => {
    $('#fileList').empty(); // 기존 목록 비우기

    if (selectedFile) {
        $('#fileList').append(`
            <li>
                ${selectedFile.name} <button type="button" class="remove-btn">X</button>
            </li>
        `);

        // X 버튼 클릭 시 파일 제거
        $('.remove-btn').on('click', function () {
            selectedFile = null; // 선택된 파일 제거
            $('#file').val(''); // 파일 input 초기화
            updateFileList(); // 파일 목록 갱신
        });
    }
};