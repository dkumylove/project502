package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.Board;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice("org.choongang")
@RequiredArgsConstructor
public class BoardAdvice {

    /**
     * @ControllerAdvice : ("") 지정된범위내에서
     *      : "예외 처리 및 공통적인 로직을 효율적으로 관리하기 위해 사용"
     *      : 전역 예외처리
     *      : @ModelAttribute 어노테이션을 사용하여 모든 컨트롤러에서 사용되는 모델 속성을 전역적으로 초기화
     *      : ViewResolver 설정과 관련된 일반적인 작업을 수행
     *      : 전역적인 데이터 바인딩: @InitBinder 어노테이션을 사용하여 데이터 바인딩을 전역적으로 설정할 수 있다
     *
     */

    private final BoardConfigInfoService configInfoService;

    @ModelAttribute("boardMenus")
    public List<Board> getBoardList() {
        // 편하게 활성화된 것들을 가져오고 싶음: 편의기능 만듬
        return configInfoService.getList();
    }
}