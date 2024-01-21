package org.choongang.board.controllers.comment;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.validators.PasswordValidator;
import org.choongang.member.MemberUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CommentFormValidator implements Validator, PasswordValidator {

    /**
     * 에너테이션으로 검증이 편하지만
     * 유동적일 경우에는 커멘드 객체에 에너테이션이 아닌
     * Validator에 추가로 검증을 넣는 것이 좋다.
     */

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestComment.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (memberUtil.isLogin()) { // 로그인 상태 일때는 비회원 비밀번호 체크 X
            return;
        }

        RequestComment form = (RequestComment) target;

        String guestPw = form.getGuestPw();
        if (!StringUtils.hasText(guestPw)) {
            errors.rejectValue("guestPw", "NotBlank");
        }

        if (StringUtils.hasText(guestPw)) {
            if (guestPw.length() < 6) {
                errors.rejectValue("guestPw", "Size");
            }

            if (!alphaCheck(guestPw, true) || !numberCheck(guestPw)) {
                errors.rejectValue("guestPw", "Complexity");
            }
        } // endif
    }
}