package com.example.repository;

import com.example.model.Member;

public interface MemberRepository {
    Member save(Member member);

    Member getBy(String loginId);
}
