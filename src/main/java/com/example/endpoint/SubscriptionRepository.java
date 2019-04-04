package com.example.endpoint;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {
}
