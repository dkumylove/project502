package org.choongang.chatting.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.choongang.chatting.controllers.ChatHistorySearch;
import org.choongang.chatting.entities.ChatHistory;
import org.choongang.chatting.entities.QChatHistory;
import org.choongang.chatting.repositories.ChatHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatHistoryInfoService {

    private final ChatHistoryRepository chatHistoryRepository;
    private final EntityManager em;

    public List<ChatHistory> getList(String roomId, ChatHistorySearch search) {

//        int page = Utils.onlyPositiveNumber(search.getPage(), 1); // 페이지 번호
//        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20); // 1페이지당 레코드 갯수
//        int offset = (page - 1) * limit; // 레코드 시작 위치 번호

        BooleanBuilder andBuilder = new BooleanBuilder();
        QChatHistory chatHistory = QChatHistory.chatHistory;

        /* roomId별로 조회 */
        andBuilder.and(chatHistory.chatRoom.roomId.eq(roomId));

        PathBuilder<ChatHistory> pathBuilder = new PathBuilder<>(ChatHistory.class, "chatHistory");

        List<ChatHistory> items = new JPAQueryFactory(em)
                .selectFrom(chatHistory)
                .leftJoin(chatHistory.member)
                .fetchJoin()
                .where(andBuilder)
                .orderBy(new OrderSpecifier(Order.ASC, pathBuilder.get("createdAt")))
                .fetch();

//        /* 페이징 처리 S */
//        int total = (int)memberRepository.count(andBuilder); // 총 레코드 갯수
//
//        Pagination pagination = new Pagination(page, total, 10, limit, request);
//        /* 페이징 처리 E */

//        return new ListData<>(items, pagination);

        return items;
    }
}
