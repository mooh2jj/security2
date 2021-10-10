package com.example.security2.config;

import com.example.security2.config.jwt.Constant;
import com.example.security2.config.jwt.JwtService;
import com.example.security2.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private HttpServletResponse response;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("username: {}", username);

        var member = memberRepository.findByUsername(username);

        if (member == null) {
            throw new UsernameNotFoundException("사용자 없음");
        }

        /**
         * 로그인 정상으로 된 이후 해당 아이디로 60초만 유효한 토큰을 발생 => 3분으로
         */
        String token = jwtService.createToken(username, 3 * 60 * 1000);

        /**
         * response header 에 생성된 토큰을 넣음
         */
        response.setHeader(Constant.AUTH_HEADER, token);

        return new SecurityUser(member);
    }
}
