package com.example.endpoint;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import javax.sql.DataSource;

@Configuration
class SubscriptionConfig {

    @Bean
    public SubscriptionService subscriptionService(final KafkaTemplate<String, SubscriptionRequest> kafkaTemplate,
                                                   final KafkaProperties kafkaProperties,
                                                   final SubscriptionRepository subscriptionRepository) {
        return new SubscriptionService(
                kafkaTemplate,
                kafkaProperties,
                subscriptionRepository);
    }

    @Bean
    public DataSource dataSource(final SubscriptionProperties subscriptionProperties) {
        final DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(subscriptionProperties.getMysqlAddress());
        dataSourceBuilder.username(subscriptionProperties.getMysqlAccountUsername());
        dataSourceBuilder.password(subscriptionProperties.getMysqlAccountPassword());
        return dataSourceBuilder.build();
    }
}
