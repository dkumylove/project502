package org.choongang.member.repositories;

import org.choongang.member.entities.Follow;
import org.choongang.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long>, QuerydslPredicateExecutor<Member> {

    // 팔로잉 리스트
    List<Follow> findByFollower(Member follower);

    // 팔로워 리스트
    List<Follow> findByFollowing(Member following);

    // 팔로우, 언팔로우 유무
    List<Follow> findByFollowerAndFollowing(Member follower, Member following);


//    // unFollow
//    void deleteByFromMemberUserIdAndToMemberUserId(int fromMember, int toMember);
}