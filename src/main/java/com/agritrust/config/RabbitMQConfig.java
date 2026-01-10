package com.agritrust.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Configuration
 * "Prevent request overload" için message queue yapılandırması
 * 
 * Queue'lar:
 * - event.queue: Ürün olaylarını (harvest, transfer, sale) async işlemek için
 * 
 * @author Reyyan (Infrastructure)
 */
@Configuration
public class RabbitMQConfig {

    // Queue isimleri
    public static final String EVENT_QUEUE = "agritrust.event.queue";
    public static final String EVENT_EXCHANGE = "agritrust.event.exchange";
    public static final String EVENT_ROUTING_KEY = "agritrust.event.routing.key";

    /**
     * Event Queue - Ürün olaylarını bufferlama için
     * Yüksek trafik anlarında istekleri kuyruğa alır
     */
    @Bean
    public Queue eventQueue() {
        return new Queue(EVENT_QUEUE, true); // durable = true (kalıcı)
    }

    /**
     * Topic Exchange - Routing key ile mesaj yönlendirme
     */
    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange(EVENT_EXCHANGE);
    }

    /**
     * Queue ile Exchange arasındaki bağlantı
     */
    @Bean
    public Binding eventBinding(Queue eventQueue, TopicExchange eventExchange) {
        return BindingBuilder
                .bind(eventQueue)
                .to(eventExchange)
                .with(EVENT_ROUTING_KEY);
    }

    /**
     * JSON formatında mesaj dönüşümü
     * DTO'ları otomatik serialize/deserialize eder
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate - Mesaj gönderimi için
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
