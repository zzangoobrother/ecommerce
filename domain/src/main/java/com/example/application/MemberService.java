package com.example.application;

import com.example.model.Member;
import com.example.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long register(String loginId, String password, String name) {
        if (memberRepository.existsBy(loginId)) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }

        return memberRepository.save(
                Member.builder()
                        .loginId(loginId)
                        .password(password)
                        .name(name)
                        .build()
        ).getId();
    }

    public void login(String loginId, String password) {
        Member member = memberRepository.getBy(loginId);
        if (!password.equals(member.getPassword())) {
            throw new IllegalArgumentException("아이디/비밀번호를 다시 입력해 주세요.");
        }
    }
}
