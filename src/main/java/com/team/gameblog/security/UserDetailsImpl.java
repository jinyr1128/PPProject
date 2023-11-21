package com.team.gameblog.security;

import com.team.gameblog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //사용자 현재 권한 리턴

        return null;
    }

    @Override
    public boolean isAccountNonExpired() {  // 사용자 계정의 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //사용자 계정이 잠겨 있는지 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //사용자의 자격 증명(패스워드 등)이 만료되지 않았는지 여부
        return true;
    }

    @Override
    public boolean isEnabled() { //사용자 계정이 활성화되었는지 여부
        return true;
    }
}
