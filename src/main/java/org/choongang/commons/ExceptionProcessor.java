package org.choongang.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.commons.exceptions.AlertRedirectException;
import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 일반적인 컨트롤러 에러를 처리
 */
public interface ExceptionProcessor {

    @ExceptionHandler(Exception.class)
    default String errorHandler(Exception e, HttpServletResponse response, HttpServletRequest request, Model model) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        // 우리가 정의한 예외이면 가져와서 구체적으로 다르게 출력하겠다는 의미
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        response.setStatus(status.value());

        e.printStackTrace();

        if (e instanceof AlertException) { // 자바스크립트 Alert형태로 응답
            String script = String.format("alert('%s');", e.getMessage());

            if (e instanceof AlertBackException) { // history.back(); 실행
                script += "history.back();";
            }

            // 페이지이동
            if (e instanceof AlertRedirectException) {
                AlertRedirectException alertRedirectException = (AlertRedirectException) e;

                script += String.format("%s.location.replace('%s');", alertRedirectException.getTarget(), alertRedirectException.getRedirectUrl());
            }

            model.addAttribute("script", script);
            return "common/_execute_script";

        }

        // 형식을 동일하게 만들고 템플릿으로 동일하게 출력
        model.addAttribute("status", status.value());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("method", request.getMethod());
        model.addAttribute("message", e.getMessage());

        return "error/common";
    }
}