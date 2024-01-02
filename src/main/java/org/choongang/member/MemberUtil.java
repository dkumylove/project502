package org.choongang.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    /**
     * 로그인 여부 확인
     * @return
     */
    public boolean isLogin() {
        return getMember() != null;
    }

    /**
     * Member값 불러오기
     * @return
     */
    public Member getMember() {
        Member member = (Member) session.getAttribute("mamber");
        return member;
    }
    
    
    public static void clearLoginData(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("NotBlank_username");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("Global_error");
    }
}
