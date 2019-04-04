package com.example.endpoint;

class SubscriptionMapper {

    static SubscriptionEntity subscriptionDtoToSubscriptionEntity(final SubscriptionDTO subscriptionDTO) {
        return new SubscriptionEntity(
                subscriptionDTO.getEmail(),
                subscriptionDTO.getFirstName(),
                subscriptionDTO.getGender(),
                subscriptionDTO.getDateOfBith(),
                subscriptionDTO.getNewsletterId(),
                subscriptionDTO.getIsConsent()
        );
    }

}
