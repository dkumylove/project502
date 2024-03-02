package org.choongang.member.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        HttpSession session = request.getSession();

        // 세션로그인 메시지 일괄 삭제
        MemberUtil.clearLoginData(session);

        /**
         * 회원정보조회 편의 구현
         * Authentication 로그인 정보가 담겨있는 객체
         */
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        Member member = memberInfo.getMember();
        session.setAttribute("member", member);

        // 회원 서비스 페이지로 이동
        String redirectURL = request.getParameter("redirectURL");
        redirectURL = StringUtils.hasText(redirectURL) ? redirectURL : "/"; // url이 있으면 그 페이지로 없으면 메인페이지로 이동

        response.sendRedirect(request.getContextPath() + redirectURL);
    }
}
