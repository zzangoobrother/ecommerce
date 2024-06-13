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

    public Long register(String name) {
        return memberRepository.save(Member.builder().name(name).build()).getId();
    }
}
