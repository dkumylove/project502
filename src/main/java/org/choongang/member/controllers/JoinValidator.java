package org.choongang.member.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator {

    private final MemberRepository memberRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         * 1. 아이디, 이메일 중복여부 체크
         * 2. 비밀번호 복잡성 체크 : 대소문자 각각 1개이상, 숫자 1개이상, 특문 1개이상 포함
         * 3. 비밀번호, 비밀번호 확인 일치여부 체크
         */

        RequestJoin form = (RequestJoin)target;
        String email = form.getEmail();
        String userId = form.getUserId();
        String password = form.getPassword();
        String confimPassword = form.getConfirmPassword();

        // 1. 아이디, 이메일 중복여부 체크
        if(StringUtils.hasText(email) && memberRepository.existsByEmail(email)) {
            errors.rejectValue("email", "Duplicated");
        }

        if(StringUtils.hasText(userId) && memberRepository.existsByUserId(userId)) {
            errors.rejectValue("userId", "Duplicated");
        }

        // 2. 비밀번호 복잡성 체크 : 대소문자 각각 1개이상, 숫자 1개이상, 특문 1개이상 포함



        // 3. 비밀번호, 비밀번호 확인 일치여부 체크
        if(StringUtils.hasText(password) && StringUtils.hasText(confimPassword) && !password.equals(confimPassword)) {
            errors.rejectValue("confirmPassword", "Mismatch.password");
        }
    }
}
