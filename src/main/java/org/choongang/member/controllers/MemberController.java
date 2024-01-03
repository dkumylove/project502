package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    private final Utils utils;
    //private final JoinValidator joinValidator;
    private final JoinService joinService;
    private final MemberUtil memberUtil;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form) {

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors) {

        joinService.process(form, errors);

        if(errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login() {
        return utils.tpl("member/login");
    }

    /*
    @GetMapping("/info")
    @ResponseBody
    public void info(Principal principal) {
        String username = principal.getName();
        System.out.printf("username = %s%n", username);
    }
    */

    /*
    @GetMapping("/info")
    @ResponseBody
    public void info(@AuthenticationPrincipal MemberInfo memberInfo) {
        System.out.println("memberInfo = " + memberInfo);
    }
    */

    /*
    @GetMapping("/info")
    @ResponseBody
    public void info() {
        MemberInfo memberInfo = (MemberInfo) SecurityContextHolder
                .getContext()
                .getAuthentication()  // 로그인 정보가 담겨있는 객체
                .getPrincipal();
        System.out.println("memberInfo = " + memberInfo);
    }
    */

    @GetMapping("/info")
    @ResponseBody
    public void info() {
        if(memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            System.out.println("member = " + member);
        } else {
            System.out.println("======== 미 로그인 상태 ========");
        }
    }
}
