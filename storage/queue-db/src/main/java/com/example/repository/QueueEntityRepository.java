package com.example.repository;

import com.example.entity.QueueEntity;
import com.example.model.Queue;
import com.example.model.Status;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class QueueEntityRepository implements QueueRepository {

    private final QueueJpaRepository repository;

    public QueueEntityRepository(QueueJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Queue getBy(Long memberId, String token) {
        return repository.findByMemberIdAndToken(memberId, token).orElseThrow(
                () -> new IllegalArgumentException("해당 대기열을 찾을 수 없습니다.")
        ).toQueue();
    }

    @Override
    public Queue save(Queue queue) {
        return repository.save(QueueEntity.toQueueEntity(queue)).toQueue();
    }

    @Override
    public int countRemainWait(Status status, LocalDateTime createdAt) {
        return repository.countByStatusAndCreatedAtLessThanEqual(status, createdAt);
    }

    @Override
    public int countBy(Status status) {
        return repository.countByStatus(status);
    }

    @Override
    public List<Queue> getAllBy(Status status, int neededCount) {
        return repository.findTopByWaitting(status, neededCount).stream()
                .map(QueueEntity::toQueue)
                .toList();
    }

    @Override
    public void saveAll(List<Queue> queues) {
        List<QueueEntity> queueEntities = queues.stream()
                .map(QueueEntity::toQueueEntity)
                .toList();

        repository.saveAll(queueEntities);
    }
}
