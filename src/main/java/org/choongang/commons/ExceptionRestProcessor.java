package org.choongang.commons;

import org.choongang.commons.exceptions.CommonException;
import org.choongang.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Rest 컨트롤러 예외처리
 * 응답이 페이지가 아닌 json방식으로 반응
 */
public interface ExceptionRestProcessor {

    @ExceptionHandler(Exception.class)
    default ResponseEntity<JSONData<Object>> errorHandler(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;  // 기본 500

        if(e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(e.getMessage());

        e.printStackTrace();

        return ResponseEntity.status(status).body(data);

    }
}
