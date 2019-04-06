package com.example.endpoint;

class SubscriptionResponse {
    private String subscriptionId;

    public SubscriptionResponse(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }
}
