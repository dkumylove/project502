package org.choongang.board.service;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.board.controllers.RequestBoard;
import org.choongang.board.entities.Board;
import org.choongang.board.entities.BoardData;
import org.choongang.board.entities.QBoardData;
import org.choongang.board.repositories.BoardDataRepository;
import org.choongang.board.repositories.BoardRepository;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.MemberUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardSaveService {

    private final BoardAuthService boardAuthService;
    private final BoardRepository boardRepository;
    private final BoardDataRepository boardDataRepository;
    private final FileUploadService fileUploadService;
    private final MemberUtil memberUtil;
    private final HttpServletRequest request;
    private final PasswordEncoder encoder;  // 비밀번호 해시화 의존성

    /**
     * 저장후 이동하는 것이 목록일 수도 있고, 상세보기일 수도 있기 때문에 리턴값은 줌
     * @return
     */
    public BoardData save(RequestBoard form) {

        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "write";

        Long seq = form.getSeq();

        // 수정 권한 체크
        if (mode.equals("update")) {
            boardAuthService.check(mode, seq);
        }

        BoardData data = null;
        if (seq != null && mode.equals("update")) { // 글 수정
            data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);
        } else { // 글 작성
            data = new BoardData();
            data.setGid(form.getGid());
            data.setIp(request.getRemoteAddr());
            data.setUa(request.getHeader("User-Agent"));
            data.setMember(memberUtil.getMember());

            Board board = boardRepository.findById(form.getBid()).orElse(null);
            data.setBoard(board);
            Long parentSeq = form.getParentSeq();
            data.setParentSeq(parentSeq); // 부모 게시글 번호

            long listOrder = parentSeq == null ?
                    System.currentTimeMillis() :
                    getReplyListOrder(parentSeq);
            data.setListOrder(listOrder);

            if (parentSeq == null) { // 본글
                data.setListOrder2("R");
            } else { // 답글
                String listOrder2 = getReplyListOrder2(parentSeq);
                data.setListOrder2(listOrder2); // A의 갯수 -> depth
                int depth = StringUtils.countOccurrencesOf(listOrder2, "A");
                data.setDepth(depth);
            }
        }

        data.setPoster(form.getPoster());
        data.setSubject(form.getSubject());
        data.setContent(form.getContent());
        data.setCategory(form.getCategory());
        data.setEditorView(data.getBoard().isUseEditor());

        // 추가 필드 - 정수
        data.setNum1(form.getNum1());
        data.setNum2(form.getNum2());
        data.setNum3(form.getNum3());

        // 추가 필드 - 한줄 텍스트
        data.setText1(form.getText1());
        data.setText2(form.getText2());
        data.setText3(form.getText3());

        // 추가 필드 - 여러줄 텍스트
        data.setLongText1(form.getLongText1());
        data.setLongText2(form.getLongText2());
        data.setLongText3(form.getLongText3());

        // 비회원 비밀번호
        String guestPw = form.getGuestPw();
        if (StringUtils.hasText(guestPw)) {
            String hash = encoder.encode(guestPw);
            data.setGuestPw(hash);
        }

        // 공지글 처리 - 관리자만 가능
        if (memberUtil.isLogin()) {
            data.setNotice(form.isNotice());
        }

        boardDataRepository.saveAndFlush(data);

        // 파일 업로드 완료 처리
        fileUploadService.processDone(data.getGid());

        return data;
    }


    /**
     * 답글 정렬 순서 번호 listOrder
     * 답글의 부모 게시글의 listOrder
     * 부모 답글이 있는 경우 부모와 동일한 listOrder 반영
     * @param parentSeq
     * @return
     */
    private long getReplyListOrder(Long parentSeq) {
        BoardData data = boardDataRepository.findById(parentSeq).orElse(null);
        if (data == null) {
            return System.currentTimeMillis();
        }

//        /**
//         * 답글이 이미 있는 경우 -> 마지막 답글 순서에서  -1
//         * 답글이 하나도 없는 경우 -> 부모 게시글 순서에서 -1
//         */
//        Long lastListOrder = boardDataRepository.getLastReplyListOrder(parentSeq);
//        if (lastListOrder == null || lastListOrder.longValue() == 0L) { // 답글이 없는 경우
//            return data.getListOrder().longValue() - 1000;
//
//        } else { // 답글이 있는 경우
//            return lastListOrder.longValue() - 1;
//        }
        return  data.getListOrder();
    }

    /**
     * 답글 2차 정렬
     *
     * @param parentSeq
     * @return
     */
    private String getReplyListOrder2(Long parentSeq) {
        BoardData data = boardDataRepository.findById(parentSeq).orElse(null);
        if (data == null) { // 처음 답글
            return "A1000";
        }

        int depth = data.getDepth() + 1; // 0 - 본글 - 답글,  1 답글 - 다답글, 2 다답글 - 다다답글

        QBoardData boardData = QBoardData.boardData;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(boardData.parentSeq.eq(parentSeq))
                .and(boardData.depth.eq(depth));

        long count = boardDataRepository.count(builder);
        long seqNum = 1000 + count;

        return data.getListOrder2() + "A" + seqNum;
    }
}