package com.example.endpoint;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class KafkaProperties {

    private String kafkaTopicName;
    private String kafkaAddress;

    KafkaProperties(@Value("${KAFKA_TOPIC_NAME}") final String kafkaTopicName,
                    @Value("${KAFKA_ADDRESS}") final String kafkaAddress) {
        this.kafkaTopicName = kafkaTopicName;
        this.kafkaAddress = kafkaAddress;
    }
}