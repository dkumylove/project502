package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Follow;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public void followUser(Member follower, Member following) {
        // 이미 팔로우한 경우 확인
        List<Follow> existingFollows = followRepository.findByFollowerAndFollowing(follower, following);

        if (existingFollows.isEmpty()) {
            // 팔로우하지 않았다면 팔로우 추가
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);
            followRepository.save(follow);
        } else {
            // 이미 팔로우한 경우 언팔로우
           // unfollowUser(follower, following);
            // 예: 에러 처리 또는 이미 팔로우했다는 메시지 반환
        }
    }

    public List<Follow> getFollowers(Member member) {
        return followRepository.findByFollowing(member);
    }

    public List<Follow> getFollowing(Member member) {
        return followRepository.findByFollower(member);
    }

}
