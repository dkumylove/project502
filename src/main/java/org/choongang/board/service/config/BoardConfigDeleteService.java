package org.choongang.board.service.config;

import lombok.RequiredArgsConstructor;
import org.choongang.board.entities.Board;
import org.choongang.board.repositories.BoardRepository;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.file.service.FileDeleteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardConfigDeleteService {

    private final Utils utils;
    private final BoardRepository boardRepository;
    private final BoardConfigInfoService configInfoService;

    private final FileDeleteService fileDeleteService;

    /**
     * 게시판 개별 삭제
     *
     * @param bid : 게시판 아이디
     */
    public void delete(String bid) {
        Board board = configInfoService.get(bid);

        String gid = board.getGid();

        boardRepository.delete(board);  // 게시판삭제

        boardRepository.flush();

        fileDeleteService.delete(gid);  // 게시판에 파일삭제

    }


    public void deleteList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("삭제할 게시판을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            String bid = utils.getParam("bid_" + chk);
            delete(bid);
        }
    }
}
