package com.agritrust.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agritrust.entity.EventEntity;
import com.agritrust.enums.EventType;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByProductBatchIdOrderByCreatedAtAsc(Long productBatchId);

    List<EventEntity> findByProductBatchIdAndEventType(Long productBatchId, List<EventType> eventTypes);
}

