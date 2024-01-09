var commonLib = commonLib || {};

/**
 * ajax 처리
 *
 * @param method : 요청 메서드 - GET, POST, PUT ...
 * @param url : 요청 URL
 * @param params : 요청 데이터 (post, put, patch ...)
 * @param responseType : json - 응답 결과를 json 변환, 아닌 경우는 문자열로 반환
 */
commonLib.ajaxLoad = function(method, url, params, responseType) {
    // 기본값 설정
    method = method || "GET";
    params = params || null;

    const token = document.querySelector("meta[name='_csrf']").content;
    const tokenHeader = document.querySelector("meta[name='_csrf_header']").content;

    // 비동기 순차 .... 가능하게 해주는
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open(method, url);

        xhr.setRequestHeader(tokenHeader, token)
        xhr.send(params);  // 요청 body에 실릴 데이터 키=값&키=값... FprmData 객체(post,patch, put)



        xhr.onreadystatechange = function() {
            if (xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE) {
                const resData = (responseType && responseType.toLowerCase() == 'json') ? JSON.parse(xhr.responseText) : xhr.responseText;

                resolve(resData);  // 성공시 응답데이터
            }
        };

        xhr.onabort = function(err) {
            reject(err);  // 중단시
        };

        xhr.onerror = function(err) {
            reject(err);  // 요청 또는 응답시 오류 발생
        };

    });
}


commonLib.ajaxLoad = function(method, url, params, responseType) {

    method = !method || !method.trim()? "GET" : method.toUpperCase();
    const token = document.querySelector("meta[name='_csrf']").content;
    const header = document.querySelector("meta[name='_csrf_header']").content;
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.setRequestHeader(header, token);

        xhr.send(params);
        responseType = responseType?responseType.toLowerCase():undefined;
        if (responseType == 'json') {
            xhr.responseType=responseType;
        }

        xhr.onreadystatechange = function() {
            if (xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE) {
                const resultData = responseType == 'json' ? xhr.response : xhr.responseText;

                resolve(resultData);
            }
        };

        xhr.onabort = function(err) {
            reject(err);
        };

        xhr.onerror = function(err) {
            reject(err);
        };

        xhr.ontimeout = function(err) {
            reject(err);
        };
    });
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
}