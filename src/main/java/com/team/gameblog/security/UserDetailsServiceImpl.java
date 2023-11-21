package com.team.gameblog.security;

import com.team.gameblog.entity.User;
import com.team.gameblog.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRespository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(username +"유저는 찾을 수 없습니다.")
        );

        return new UserDetailsImpl(user);
    }

}
