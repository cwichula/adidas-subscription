package com.example.endpoint;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Table(name = "subscriptions")
@Entity
@Getter
@Setter
@ToString
class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotEmpty
    private String email;

    private String firstName;

    private String gender;

    @NotEmpty
    private String dateOfBith;

    @NotEmpty
    private String newsletterId;

    @NotEmpty
    private String isConsent;

    SubscriptionEntity() {
    }

    SubscriptionEntity(@NotEmpty final String email,
                       final String firstName,
                       final String gender,
                       @NotEmpty final String dateOfBith,
                       @NotEmpty final String newsletterId,
                       @NotEmpty final String isConsent) {
        this.email = email;
        this.firstName = firstName;
        this.gender = gender;
        this.dateOfBith = dateOfBith;
        this.newsletterId = newsletterId;
        this.isConsent = isConsent;
    }
}
