package com.example.repository;

import com.example.model.Member;

import java.util.HashMap;
import java.util.Map;

public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> members = new HashMap<>();

    @Override
    public Member save(Member member) {
        members.put(member.getId(), member);
        return member;
    }

    @Override
    public Member getBy(String loginId) {
        return members.values().stream()
                .filter(it -> it.getLoginId().equals(loginId))
                .findFirst().get();
    }

    @Override
    public boolean existsBy(String loginId) {
        return members.values().stream()
                .anyMatch(it -> it.getLoginId().equals(loginId));
    }
}
