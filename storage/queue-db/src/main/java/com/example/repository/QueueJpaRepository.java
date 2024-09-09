package com.example.repository;

import com.example.entity.QueueEntity;
import com.example.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Long> {

    Optional<QueueEntity> findByMemberIdAndToken(Long memberId, String token);

    Optional<QueueEntity> findByToken(String token);

    int countByStatusAndCreatedAtLessThanEqual(Status status, LocalDateTime createdAt);

    int countByStatus(Status status);

    @Query("select queue from QueueEntity queue where status = :status order by createdAt asc limit :neededCount")
    List<QueueEntity> findTopByWaitting(@Param("status") Status status, @Param("neededCount") int neededCount);

    List<QueueEntity> findAllByStatus(Status status);
}
