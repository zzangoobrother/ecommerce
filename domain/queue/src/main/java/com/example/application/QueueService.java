package com.example.application;

import com.example.application.dto.QueueDto;
import com.example.model.Queue;
import com.example.model.Status;
import com.example.repository.QueueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueService {
    private final static int ALLOWED_QUEUE_SIZE = 1;

    private final QueueRepository queueRepository;

    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    public void checkedByProcessing(String token) {
        Queue queue = queueRepository.getBy(token);
        if (!queue.checkedProcessing()) {
            throw new IllegalStateException("해당 토큰을 확인해주세요.");
        }
    }

    public String create(Long memberId) {
        String token = TokenSequence.create(LocalDateTime.now());
        Queue queue = Queue.builder()
                .memberId(memberId)
                .token(token)
                .status(Status.WAITING)
                .createdAt(LocalDateTime.now())
                .build();

        queueRepository.save(queue);
        return token;
    }

    public QueueDto getBy(Long memberId, String token) {
        Queue queue = queueRepository.getBy(memberId, token);

        int remainWaitCount = 0;
        if (queue.checkedWaitting()) {
            remainWaitCount = queueRepository.countRemainWait(Status.WAITING, queue.getCreatedAt());
        }

        return new QueueDto(
                queue.getId(),
                queue.getStatus(),
                queue.getCreatedAt(),
                remainWaitCount
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void maintainProcessing() {
        int count = queueRepository.countBy(Status.PROCESSING);
        int neededCount = ALLOWED_QUEUE_SIZE - count;

        if (neededCount == 0) {
            return;
        }

        List<Queue> queues = queueRepository.getAllBy(Status.WAITING, neededCount);
        queues.forEach(Queue::updateProcessing);

        queueRepository.saveAll(queues);
    }

    public void complete(String token) {
        Queue queue = queueRepository.getBy(token);
        queue.updateComplete();
        queueRepository.save(queue);
    }

    public void deletedCompleted() {
        List<Queue> queues = queueRepository.getByStatus(Status.COMPLETED);
        List<Long> queueIds = queues.stream()
                .map(Queue::getId)
                .toList();
        queueRepository.deleteByCompleted(queueIds);
    }
}
