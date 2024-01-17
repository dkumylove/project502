package org.choongang.board.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode  // 동등성비교
@NoArgsConstructor
@AllArgsConstructor
public class BoardViewId {

    private Long seq; // 게시글 번호
    private int uid; // viewUid
}
