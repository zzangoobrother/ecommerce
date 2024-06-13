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
        return repository.save(new MemberEntity(member.getName())).toMember();
    }
}
