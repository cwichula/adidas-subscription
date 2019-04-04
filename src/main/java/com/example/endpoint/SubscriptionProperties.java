package com.example.endpoint;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class SubscriptionProperties {

    private String mysqlAddress;
    private String mysqlAccountUsername;
    private String mysqlAccountPassword;

    SubscriptionProperties(
            @Value("${MYSQL_ADDRESS}") final String mysqlAddress,
            @Value("${MYSQL_ACCOUNT_USERNAME}") final String mysqlAccountUsername,
            @Value("${MYSQL_ACCOUNT_PASSWORD}") final String mysqlAccountPassword) {
        this.mysqlAddress = mysqlAddress;
        this.mysqlAccountUsername = mysqlAccountUsername;
        this.mysqlAccountPassword = mysqlAccountPassword;
    }
}
