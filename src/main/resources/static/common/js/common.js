var commonLib = commonLib || {}; // 파일 속성값으로 namespace를 정할 수 있음 / 있으면 사용 없으면 만들기

/**
 * ajax 요청, 응답 편의 함수
 * @param method : 요청 방식(GET, POST, PUT, PATCH, DELETE ... )
 * @param url : 요청 URL
 * @param params : 요청 데이터 -> post, put patch..(바디 쪽에 실릴 데이터)
 * @param responseType : json -> JSON형태 (자바스크립트 객체)로 변환
 */
commonLib.ajaxLoad = function(method, url, params, responseType, headers) {
    method = method || "GET";
    params = params || null;

    const token = document.querySelector("meta[name='_csrf']").content;
    const tokenHeader = document.querySelector("meta[name='_csrf_header']").content;

    return new Promise((resolve, reject) => { // 비동기 순차실행을 가능하게 해줌, resolve는 성공시 reject는 실패시
        const xhr = new XMLHttpRequest();

        xhr.open(method, url);
        xhr.setRequestHeader(tokenHeader, token);

        if (headers) {
            for (const key in headers) {
                xhr.setRequestHeader(key, headers[key]);
            }
        }

        xhr.send(params); // 요청 바디에 실릴 데이터를 넣어줌 형식은 쿼리스트링(키=값)도 가능, formdata 객체(post, patch, put) 형태도 가능

        /*responseType = responseType?responseType.toLowerCase():undefined;
        if (responseType == 'json') {
            xhr.responseType=responseType;
        }*/


        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                // 제이슨이면 자바스크립트로 바꾸고,, 아닐때는 문자열 형태로
                // 바꾸는 과정
                const resData = (xhr.responseText.trim() && responseType && responseType.toLowerCase() == 'json') ? JSON.parse(xhr.responseText) : xhr.responseText;

                if (xhr.status == 200 || xhr.status == 201) {

                    resolve(resData); // 성공시 응답 데이터
                } else {
                    reject(resData);
                }
            }
        };


        // 실패 했을 때
        xhr.onabort = function(err) {
            reject(err); // 중단 시
        };

        xhr.onerror = function(err) {
            reject(err); // 요청 또는 응답시 오류 발생
        };

        /*xhr.ontimeout = function(err) {
            reject(err);
        };*/
    });
};


/**
 * 이메일 인증 메일 보내기
 *
 * @param email : 인증할 이메일
 */
commonLib.sendEmailVerify = function(email) {
    const { ajaxLoad } = commonLib;

    const url = `/api/email/verify?email=${email}`;

    ajaxLoad("GET", url, null, "json")
        .then(data => {
            if (typeof callbackEmailVerify == 'function') { // 이메일 승인 코드 메일 전송 완료 후 처리 콜백
                callbackEmailVerify(data);
            }
        })
        .catch(err => console.error(err));
};

/**
 * 인증 메일 코드 검증 처리
 *
 */
commonLib.sendEmailVerifyCheck = function(authNum) {
    const { ajaxLoad } = commonLib;
    const url = `/api/email/auth_check?authNum=${authNum}`;

    ajaxLoad("GET", url, null, "json")
        .then(data => {
            if (typeof callbackEmailVerifyCheck == 'function') { // 인증 메일 코드 검증 요청 완료 후 처리 콜백
                callbackEmailVerifyCheck(data);
            }
        })
        .catch(err => console.error(err));
};


/**
 * 위지윗 에디터 로드
 * @param id
 * @param height
 * @returns {*}
 */
commonLib.loadEditor = function(id, height) {
    if (!id) {
        return;
    }

    height = height || 450;

    // ClassicEditor
    return ClassicEditor.create(document.getElementById(id), {
        height
    });
};

/* 최근 본 게시글 기록 저장 S */
if (location.pathname.indexOf("/board/view/") != -1) {
    const v = /\/board\/view\/(\d*)/.exec(location.pathname);
    const seq = Number(v[1]);
    const key = "viewPosts";
    let viewPosts = localStorage.getItem(key);
    viewPosts = JSON.parse(viewPosts) || [];

    // 이미 열람 기록이 있으면 기존 기록 삭제
    const index = viewPosts.findIndex(p => p.seq == seq);
    if (index != -1) viewPosts.splice(index, 1);

    viewPosts.push({seq, timestamp: Date.now()});

    viewPosts.sort((a, b) => b.timestamp - a.timestamp); // 내림차순 정렬

    localStorage.setItem(key, JSON.stringify(viewPosts));
}
/* 최근 본 게시글 기록 저장 E */