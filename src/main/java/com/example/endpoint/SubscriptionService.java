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

    private KafkaTemplate<String, SubscriptionRequest> kafkaTemplate;
    private KafkaProperties kafkaProperties;
    private SubscriptionRepository subscriptionRepository;

    public Long publish(SubscriptionRequest subscriptionRequest) {
        final SubscriptionEntity saved = saveToDatabase(subscriptionRequest);
        logger.info("Subscription entity saved: {}", saved);
        sendToKafka(subscriptionRequest);
        return saved.getId();
    }

    private void sendToKafka(final SubscriptionRequest subscription) {
        final ListenableFuture<SendResult<String, SubscriptionRequest>> futureResponse = kafkaTemplate.send(
                kafkaProperties.getKafkaTopicName(),
                subscription);

        futureResponse.addCallback(new ListenableFutureCallback<SendResult<String, SubscriptionRequest>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("Failed kafka response", ex);
                throw new SubscriptionException(String.format("Failed kafka response: %s ", ex.getMessage()));
            }

            @Override
            public void onSuccess(SendResult<String, SubscriptionRequest> result) {
                logger.info(String.format("TOPIC: %s; Producer record: %s Record metadata: %s",
                                          kafkaProperties.getKafkaTopicName(),
                                          result.getProducerRecord(),
                                          result.getRecordMetadata()));
            }
        });
    }

    private SubscriptionEntity saveToDatabase(final SubscriptionRequest subscriptionRequest) {
        final SubscriptionEntity subscriptionEntity = SubscriptionMapper.subscriptionDtoToSubscriptionEntity(
                subscriptionRequest);
        return subscriptionRepository.save(subscriptionEntity);
    }
}
