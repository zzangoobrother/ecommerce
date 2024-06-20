package com.example.repository;

import com.example.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
