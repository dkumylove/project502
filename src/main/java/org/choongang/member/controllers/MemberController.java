package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes("EmailAuthVerified")
public class MemberController implements ExceptionProcessor {

    private final Utils utils;
    //private final JoinValidator joinValidator;
    private final JoinService joinService;
    //private final MemberUtil memberUtil;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {

        commonProcess("join", model);

        // 이메일 인증 여부 false로 초기화
        model.addAttribute("EmailAuthVerified", false);

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model, SessionStatus sessionStatus) {

        commonProcess("join", model);

        joinService.process(form, errors);

        if(errors.hasErrors()) {
            return utils.tpl("member/join");
        }
        /* EmailAuthVerified 세션값 비우기 */
        sessionStatus.setComplete();

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        commonProcess("login", model);
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
    */

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");

        List<String> addCommonScript = new ArrayList<>();  // 공통 자바스크립트
        List<String> addScript = new ArrayList<>();  // 프론트 자바 스크립트

        if(mode.equals("login")) {
            pageTitle = Utils.getMessage("로그인", "commons");
        } else if (mode.equals("join")) {
            addCommonScript.add("fileManager");
            addScript.add("member/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}
