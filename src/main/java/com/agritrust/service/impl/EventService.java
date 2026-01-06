package com.agritrust.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agritrust.dto.EventDto;
import com.agritrust.dto.EventRequestDto;
import com.agritrust.entity.EventEntity;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;
import com.agritrust.enums.EventType;
import com.agritrust.repos.EventRepository;
import com.agritrust.service.EventReadableAndWritable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService implements EventReadableAndWritable {

	private final EventRepository eventRepo;
	private final ModelMapper modelMapper;

	@Override
	public List<EventEntity> getList() {
		return eventRepo.findAll();
	}

	@Override
	public EventEntity getById(Long id) {
		return eventRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
	}

	@Override
	@Transactional(readOnly = true)
	public List<EventDto> getFullTrace(Long productBatchId) {
		return eventRepo.findByProductBatchIdOrderByCreatedAtAsc(productBatchId).stream()
				.map(e -> modelMapper.map(e, EventDto.class)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<EventDto> getProcessingTrace(Long productBatchId) {
		return eventRepo.findByProductBatchIdAndEventType(productBatchId, List.of(EventType.HARVEST, EventType.PROCESSING))
				.stream().map(e -> modelMapper.map(e, EventDto.class)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<EventDto> getLogisticsTrace(Long productBatchId) {
		return eventRepo.findByProductBatchIdAndEventType(productBatchId,List.of(EventType.TRANSFER))
				.stream().map(e -> modelMapper.map(e, EventDto.class)).toList();
	}

	@Override
	@Transactional
	public void recordEvent(ProductBatchEntity batch, UserEntity actor, EventRequestDto eventDto) {
        EventEntity event = modelMapper.map(eventDto, EventEntity.class);

        event.setEventType(eventDto.getEventType());
        event.setProductBatch(batch);
        event.setActor(actor);

        eventRepo.save(event);

	}

}
