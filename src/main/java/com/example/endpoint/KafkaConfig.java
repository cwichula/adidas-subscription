package com.example.endpoint;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
class KafkaConfig {

    @Bean
    public ProducerFactory<String, SubscriptionRequest> producerFactory(final KafkaProperties kafkaProperties) {
        final Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getKafkaAddress());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        return new DefaultKafkaProducerFactory(props);
    }

    @Bean
    public KafkaTemplate<String, SubscriptionRequest> kafkaTemplate(final ProducerFactory<String, SubscriptionRequest> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
