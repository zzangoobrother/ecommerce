package com.example.repository;

import com.example.model.Member;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> members = new HashMap<>();

    @Override
    public Member save(Member member) {
        if (Objects.isNull(member.getId())) {
            int size = members.size();
            member = Member.builder()
                    .id(size == 0 ? 1 : Collections.max(members.keySet()) + 1)
                    .loginId(member.getLoginId())
                    .password(member.getPassword())
                    .name(member.getName())
                    .build();
        }

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
