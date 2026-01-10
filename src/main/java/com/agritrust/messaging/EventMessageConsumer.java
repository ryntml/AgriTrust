package com.agritrust.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.agritrust.config.RabbitMQConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RabbitMQ Message Consumer
 * Queue'dan gelen event mesajlarını işler
 * 
 * Bu consumer, async olarak çalışır ve yüksek trafik anlarında
 * bufferlanan mesajları sırayla işler.
 * 
 * Örnek kullanım alanları:
 * - Event logging (audit trail)
 * - Analytics/istatistik hesaplama
 * - Notification gönderimi
 * - External sistem entegrasyonları
 * 
 * @author Reyyan (Infrastructure)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventMessageConsumer {

    /**
     * Queue'dan gelen event mesajlarını dinler ve işler
     * 
     * @param message EventMessageDto objesi (otomatik deserialize edilir)
     */
    @RabbitListener(queues = RabbitMQConfig.EVENT_QUEUE)
    public void consumeEventMessage(EventMessageDto message) {
        log.info("📥 Received event from queue:");
        log.info("   Event Type: {}", message.getEventType());
        log.info("   Batch Code: {}", message.getBatchCode());
        log.info("   Actor: {}", message.getActorUsername());
        log.info("   Timestamp: {}", message.getTimestamp());

        // Burada async işlemler yapılabilir:
        // - Audit log kaydetme
        // - Analytics güncelleme
        // - Email/notification gönderme
        // - External API çağrıları

        processEventAsync(message);
    }

    /**
     * Event'i async olarak işler
     * Ana API thread'ini bloklamaz
     */
    private void processEventAsync(EventMessageDto message) {
        // Örnek: Event tipine göre farklı işlemler
        switch (message.getEventType()) {
            case HARVEST:
                log.info("🌾 Processing HARVEST event for batch: {}", message.getBatchCode());
                // Hasat istatistiklerini güncelle
                break;
            case TRANSFER:
                log.info("🚚 Processing TRANSFER event: {} -> {}",
                        message.getFromLocation(), message.getToLocation());
                // Lojistik takibi güncelle
                break;
            case SALE:
                log.info("💰 Processing SALE event for batch: {}", message.getBatchCode());
                // Satış raporlarını güncelle
                break;
            case PROCESSING:
                log.info("⚙️ Processing PROCESSING event for batch: {}", message.getBatchCode());
                break;
            case CERTIFICATE_ADD_OR_REVOKE:
                log.info("📜 Processing CERTIFICATE event for batch: {}", message.getBatchCode());
                break;
            default:
                log.warn("Unknown event type: {}", message.getEventType());
        }

        log.info("✅ Event processed successfully");
    }
}
