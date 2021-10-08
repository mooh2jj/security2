package com.example.security2.repository;

import com.example.security2.domain.Member;
import com.example.security2.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void A001_insert() {
        Member member = new Member();

        member.setId(1L);
        member.setUsername("admin");
        member.setPassword(passwordEncoder.encode("admin"));
        member.setEnabled(true);
        member.setDname("admin 부서");
        member.setRole(Role.ROLE_ADMIN);

        memberRepository.save(member);

        member.setId(2L);
        member.setUsername("manager");
        member.setPassword(passwordEncoder.encode("manager"));
        member.setEnabled(true);
        member.setDname("manager 부서");
        member.setRole(Role.ROLE_MANAGER);

        memberRepository.save(member);

        member.setId(3L);
        member.setUsername("member");
        member.setPassword(passwordEncoder.encode("member"));
        member.setEnabled(true);
        member.setDname("member 부서");
        member.setRole(Role.ROLE_MEMBER);

        memberRepository.save(member);
    }
    
}