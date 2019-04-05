package com.example.endpoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class SubscriptionMapper {

    static SubscriptionEntity subscriptionDtoToSubscriptionEntity(final SubscriptionDTO subscriptionDTO) {
        final String dateOfBith = subscriptionDTO.getDateOfBith();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final LocalDate localDate = LocalDate.parse(dateOfBith, dateTimeFormatter);

        return new SubscriptionEntity(
                subscriptionDTO.getEmail(),
                subscriptionDTO.getFirstName(),
                subscriptionDTO.getGender(),
                localDate,
                subscriptionDTO.getNewsletterId(),
                subscriptionDTO.getIsConsent());
    }

}
