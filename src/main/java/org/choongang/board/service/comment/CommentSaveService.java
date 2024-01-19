package org.choongang.board.service.comment;

import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.comment.RequestComment;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.board.repositories.CommentDateRepository;
import org.choongang.member.MemberUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentSaveService {

    private final CommentDateRepository commentDateRepository;
    private final BoardDataRepository boardDataRepository;
    private final MemberUtil memberUtil;
    private final PasswordEncoder encoder;


    public void save(RequestComment form) {


    }
}
