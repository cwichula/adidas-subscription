package com.example.endpoint;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@AllArgsConstructor
class SubscriptionService {
    private final static Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    private KafkaTemplate<String, SubscriptionDTO> kafkaTemplate;
    private KafkaProperties kafkaProperties;
    private SubscriptionRepository subscriptionRepository;

    public Long publish(SubscriptionDTO subscriptionDTO) {
        final SubscriptionEntity saved = saveToDatabase(subscriptionDTO);
        logger.info("Subscription entity saved: {}", saved);
        sendToKafka(subscriptionDTO);
        return saved.getId();
    }

    private void sendToKafka(final SubscriptionDTO subscription) {
        final ListenableFuture<SendResult<String, SubscriptionDTO>> futureResponse = kafkaTemplate.send(
                kafkaProperties.getKafkaTopicName(),
                subscription);

        futureResponse.addCallback(new ListenableFutureCallback<SendResult<String, SubscriptionDTO>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("Failed kafka response", ex);
                throw new SubscriptionException(String.format("Failed kafka response: %s ", ex.getMessage()));
            }

            @Override
            public void onSuccess(SendResult<String, SubscriptionDTO> result) {
                logger.info(String.format("TOPIC: %s; Producer record: %s Record metadata: %s",
                        kafkaProperties.getKafkaTopicName(),
                        result.getProducerRecord(),
                        result.getRecordMetadata()));
            }
        });
    }

    private SubscriptionEntity saveToDatabase(final SubscriptionDTO subscriptionDTO) {
        final SubscriptionEntity subscriptionEntity = SubscriptionMapper.subscriptionDtoToSubscriptionEntity(subscriptionDTO);
        return subscriptionRepository.save(subscriptionEntity);
    }
}
