package com.example.endpoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class SubscriptionMapper {

    static SubscriptionEntity subscriptionDtoToSubscriptionEntity(final SubscriptionRequest subscriptionRequest) {
        final String dateOfBith = subscriptionRequest.getDateOfBith();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final LocalDate localDate = LocalDate.parse(dateOfBith, dateTimeFormatter);

        return new SubscriptionEntity(
                subscriptionRequest.getEmail(),
                subscriptionRequest.getFirstName(),
                subscriptionRequest.getGender(),
                localDate,
                subscriptionRequest.getNewsletterId(),
                subscriptionRequest.getIsConsent());
    }

}
