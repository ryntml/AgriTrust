package com.agritrust.service;

import java.util.List;

import com.agritrust.dto.EventDto;
import com.agritrust.dto.EventRequestDto;
import com.agritrust.entity.EventEntity;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;

public interface EventReadableAndWritable extends Readable<EventEntity, Long> {
	List<EventDto> getFullTrace(Long productBatchId);

	List<EventDto> getProcessingTrace(Long productBatchId);

	List<EventDto> getLogisticsTrace(Long productBatchId);

	void recordEvent(ProductBatchEntity batch, UserEntity actor, EventRequestDto eventDTO);
}
