package org.choongang.board.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.BoardDataSearch;
import org.choongang.board.entities.Board;
import org.choongang.board.entities.BoardData;
import org.choongang.board.entities.QBoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.board.service.config.BoardConfigInfoService;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardInfoService {

    private final EntityManager em; // jpa 쿼리 사용할때사용하는 의존성
    private final BoardDataRepository boardDataRepository;
    private final BoardConfigInfoService configInfoService; // 게시판 설정을 가져오기 위한 의존성
    private final FileInfoService fileInfoService;
    private final HttpServletRequest request;

    /**
     * 게시글 조회
     * @param seq : 게시글 번호
     * @return
     */
    public BoardData get(Long seq) {
        BoardData boardData = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);

        addBoardData(boardData);

        return boardData;
    }

//    /**
//     * 게시판 목록 조회
//     * @return
//     */
//    private ListData<BoardData> getList(BoardDataSearch search) {
//        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
//
//
//        String bid = search.
//    }

    /**
     * 게시판 목록 조회 - 특정 게시판
     * @param bid
     * @param search
     * @return
     */
    public ListData<BoardData> getList(String bid, BoardDataSearch search) {

        Board board = configInfoService.get(bid);

        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), board.getRowsPerPage());
        int offset = (page - 1) * limit; // 레코드 시작 위치

        QBoardData boardData = QBoardData.boardData;
        BooleanBuilder andBuilder = new BooleanBuilder();


        PathBuilder<BoardData> pathBuilder = new PathBuilder<>(BoardData.class, "boardData");

        List<BoardData> items = new JPAQueryFactory(em)
                .selectFrom(boardData)
                .leftJoin(boardData.member)
                .fetchJoin()
                .offset(offset)
                .limit(limit)
                .where(andBuilder)
                .orderBy(
                        new OrderSpecifier(Order.DESC, pathBuilder.get("notice")),
                        new OrderSpecifier(Order.DESC, pathBuilder.get("createdAt"))
                )
                .fetch();

        // 게시글 전체 갯수
        long total = boardDataRepository.count(andBuilder);
        int ranges = 10;
        Pagination pagination = new Pagination(page, (int)total, ranges, limit, request);

        return new ListData<>(items, pagination);
    }

    /**
     * 게시글 추가 정보 처리
     * @param boardData
     */
    public void addBoardData(BoardData boardData) {
        String gid = boardData.getGid();

        List<FileInfo> editorFiles = fileInfoService.getListDone(gid, "editor");
        List<FileInfo> attachFiles = fileInfoService.getListDone(gid, "attach");

        boardData.setEditorFiles(editorFiles);
        boardData.setAttachFiles(attachFiles);
    }
}
