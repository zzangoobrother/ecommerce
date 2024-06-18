package com.example.repository;

import com.example.entity.MemberEntity;
import com.example.model.Member;
import org.springframework.stereotype.Repository;

@Repository
class MemberEntityRepository implements MemberRepository {

    private final MemberJpaRepository repository;

    public MemberEntityRepository(MemberJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Member save(Member member) {
        return repository.save(
                MemberEntity.builder()
                        .loginId(member.getLoginId())
                        .password(member.getPassword())
                        .name(member.getName())
                        .build()
        ).toMember();
    }

    @Override
    public Member getBy(String loginId) {
        return repository.findByLoginId(loginId).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디를 찾을 수 없습니다.")
        ).toMember();
    }
}
