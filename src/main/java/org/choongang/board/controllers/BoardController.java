package org.choongang.board.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.Board;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController implements ExceptionProcessor {
    private final BoardDataRepository boardDataRepository;

    /**
     * 쓰기 수정 삭제, 목록 등
     */

    private final Utils utils;
    private final BoardConfigInfoService configInfoService;
    private final MemberUtil memberUtil;
    private Board board;  //게시판 설정

    /**
     * 게시판 목록
     * @param bid : 게시판 아이디
     * @param model
     * @return
     */
    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid, Model model) {

        commonProcess(bid, "list", model);

        return utils.tpl("board/list");
    }


    /**
     * 게시물 보기
     * @param seq
     * @param model
     * @return
     */
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq")Long seq, Model model) {

        commonProcess(seq, "view", model);

        return utils.tpl("board/view");
    }

    /**
     * 게시글 작성하기
     * @param bid
     * @param model
     * @return
     */
    @GetMapping("/write/{bid}")
    public String write(@PathVariable("bid")String bid,@ModelAttribute RequestBoard form, Model model ) {

        commonProcess(bid, "write", model);

        if(memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            form.setPoster(member.getName());
        }

        return utils.tpl("board/write");
    }

    /**
     * 게시글 수정하기
     * @param seq
     * @param model
     * @return
     */
   @GetMapping("/update/{seq}")
    private String update(@PathVariable("seq")Long seq, Model model) {

       commonProcess(seq, "update", model);

       return utils.tpl("board/update");
   }

    /**
     * 게시글 등록, 수정
     * @param model
     * @return
     */
    @PostMapping("/save")
   public String save(@Valid RequestBoard form, Errors errors, Model model) {

        String bid = form.getBid();
        String mode = form.getMode();

        commonProcess(form.getBid(), form.getMode(), model);

        if (errors.hasErrors()) {
            return utils.tpl("board/" + mode);
        }

        Long seq = 0L; // 임시
        String redirectURL = "/board/";
        redirectURL += board.getLocationAfterWriting() == "view" ? "view/" + seq : "list/" + form.getBid();

       return null;
   }


    /**
     * 게시판 공통 처리 - 글목록, 글쓰기 등 게시판 ID가 있는 경우
     *
     * @param bid : 게시판 id
     * @param mode
     * @param model
     */
   private void commonProcess(String bid, String mode, Model model) {

       mode = StringUtils.hasText(mode) ? mode : "list"; // 없으면 기본값 list

       List<String> addCommonScript = new ArrayList<>();  // 공통 스크립트
       List<String> addScript = new ArrayList<>();  // 프론튼, 모바일쪽 스크립트

       List<String> addCommonCss = new ArrayList<>();  // 공통 css
       List<String> addCss = new ArrayList<>();  // 프론튼, 모바일쪽 css

       /* 게시판 설정 처리 s */
//       if(board == null) {
//           board = configInfoService.get(bid);
//       }  // board 한번 바꾸면 안바뀌게 설정함. 문제: DB에 변경되어도 새롭게 만들지 않음
       board = configInfoService.get(bid);

       //스킨별 css, js추가
       String skin = board.getSkin();
       addScript.add("board/skin_"+ skin);
       addCss.add("board/skin_"+ skin);

       model.addAttribute("board", board);
       /* 게시판 설정 처리 e */

       String pageTitle = board.getBName(); // 게시판명이 기본 타이틀

       if (mode.equals("write") || mode.equals("update")) { // 쓰기 또는 수정
           if (board.isUseEditor()) { //에디터 사용하는 경우
               addCommonScript.add("ckeditor5/ckeditor");
           }
           // 이미지 또는 파일첨부를 사용하는 경우
           if (board.isUseUploadImage() || board.isUseUploadFile()) {
               addCommonScript.add("fileManager");
           }

           addScript.add("board/form");

           // 제목 타이틀
           pageTitle += " ";
           pageTitle += mode.equals("update") ? Utils.getMessage("글수정", "commons") : Utils.getMessage("글쓰기", "commons");
       }

       model.addAttribute("addCommonScript", addCommonScript);
       model.addAttribute("addScript", addScript);
       model.addAttribute("addCommonCss", addCommonCss);
       model.addAttribute("addCss", addCss);
       model.addAttribute("pageTitle", pageTitle);

   }

    /**
     * 게시판 공통 처리 : 게시글 보기, 게시글 수정 - 게시글 번호가 있는 경우
     *      - 게시글 조회 -> 게시판 설정
     * @param seq
     * @param mode
     * @param model
     */
   private void commonProcess(Long seq, String mode, Model model) {

   }
}
