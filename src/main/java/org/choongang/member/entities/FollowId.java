package org.choongang.member.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FollowId {

    private Long seq;  //팔로워한 회원번호
    private Member member;
}