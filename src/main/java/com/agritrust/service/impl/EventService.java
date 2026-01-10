package com.agritrust.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agritrust.dto.EventDto;
import com.agritrust.dto.EventRequestDto;
import com.agritrust.dto.TransferDto;
import com.agritrust.entity.EventEntity;
import com.agritrust.entity.ProductBatchEntity;
import com.agritrust.entity.UserEntity;
import com.agritrust.enums.EventType;
import com.agritrust.messaging.EventMessageDto;
import com.agritrust.messaging.EventMessageProducer;
import com.agritrust.repos.EventRepository;
import com.agritrust.service.EventReadableAndWritable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService implements EventReadableAndWritable {

	private final EventRepository eventRepo;
	private final ModelMapper modelMapper;
	private final EventMessageProducer eventMessageProducer; // RabbitMQ Producer

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
		return eventRepo
				.findByProductBatchIdAndEventTypeIn(productBatchId, List.of(EventType.HARVEST, EventType.PROCESSING))
				.stream().map(e -> modelMapper.map(e, EventDto.class)).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<EventDto> getLogisticsTrace(Long productBatchId) {
		return eventRepo.findByProductBatchIdAndEventTypeIn(productBatchId, List.of(EventType.TRANSFER))
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

		// RabbitMQ: Event'i async işleme için queue'ya gönder
		// "Prevent request overload" - yüksek trafik anlarında bufferlama
		sendEventToQueue(batch, actor, event, eventDto);
	}

	/**
	 * Event'i RabbitMQ queue'suna gönderir
	 * Async işleme için (analytics, logging, notifications vb.)
	 */
	private void sendEventToQueue(ProductBatchEntity batch, UserEntity actor,
			EventEntity event, EventRequestDto eventDto) {
		EventMessageDto message = EventMessageDto.builder()
				.productBatchId(batch.getId())
				.batchCode(batch.getBatchCode())
				.actorId(actor.getId())
				.actorUsername(actor.getUsername())
				.eventType(event.getEventType())
				.price(event.getPrice())
				.quantity(event.getQuantity())
				.description(event.getDescription())
				.timestamp(LocalDateTime.now())
				.build();

		// Transfer event için lokasyon bilgisi ekle
		if (eventDto instanceof TransferDto transferDto) {
			message.setFromLocation(transferDto.getFromLocation());
			message.setToLocation(transferDto.getToLocation());
		}

		eventMessageProducer.sendEventMessage(message);
	}
}
