package org.choongang.member.service;

import lombok.Builder;
import lombok.Data;

import org.choongang.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String userId;
    private String password;
    private Member member;
    private boolean enable;
    private boolean lock;


    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return StringUtils.hasText(email) ? email : userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }  // 회원차단할때 사용할떄도 사용

    @Override
    public boolean isAccountNonLocked() {
        return !lock;
    }  // 비번 오래되었을떄 비번 바꾸라고 할때 사용

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 회원탈되하는 부분
     * 탈퇴한다고 회원정보를 삭제하는것이아닌 비활성화하는 것이 필요
     * @return
     */
    @Override
    public boolean isEnabled() {
        return enable;
    }
}
