package org.choongang.board.controllers.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 커맨드객체
 */
@Data
public class RequestComment {

    private String mode = "add";

    private Long seq;  // 댓글 등록번호

    private Long boardDataSeq; // 게시슬 번호 : 어떤 게시물에 묶여있는 댓글 인지 확인

    @NotBlank
    private String commenter;  // 작성자

    private String guestPw;  // 비회원 비밀번호

    @NotBlank
    private String content;  // 댓글내용
}
