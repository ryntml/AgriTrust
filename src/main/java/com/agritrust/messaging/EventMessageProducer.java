package com.agritrust.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.agritrust.config.RabbitMQConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RabbitMQ Message Producer
 * Event'leri queue'ya gönderir - async işleme için
 * 
 * Kullanım amacı: "Prevent request overload"
 * Yüksek trafik anlarında event kayıtlarını kuyruğa alır,
 * böylece ana API yanıt süresi etkilenmez.
 * 
 * @author Reyyan (Infrastructure)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Event mesajını queue'ya gönderir
     * 
     * @param message EventMessageDto objesi
     */
    public void sendEventMessage(EventMessageDto message) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EVENT_EXCHANGE,
                    RabbitMQConfig.EVENT_ROUTING_KEY,
                    message);
            log.info("✅ Event message sent to queue: {} - BatchCode: {}",
                    message.getEventType(), message.getBatchCode());
        } catch (Exception e) {
            log.error("❌ Failed to send event message to queue: {}", e.getMessage());
            // Queue'ya gönderilemezse sessizce devam et (fire-and-forget)
            // Ana iş mantığını etkilemesin
        }
    }
}
