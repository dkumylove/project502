package org.choongang.member.repositories;

import org.choongang.member.entities.Follow;
import org.choongang.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long>, QuerydslPredicateExecutor<Member> {

    // 팔로잉 리스트
    public List<Follow> findByFromMemberUserId(int fromMember);

    // 팔로워 리스트
    public List<Follow> findByToMemberUserId(int toMember);

    // 팔로우, 언팔로우 유무
    public int findByFromMemberUserIdAndToMemberUserId(int fromMember, int toMember);

    // unFollow
    @Transactional
    public void deleteByFromMemberUserIdAndToMemberUserId(int fromMember, int toMember);
}