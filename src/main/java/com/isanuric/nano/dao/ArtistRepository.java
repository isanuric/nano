package com.isanuric.nano.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, Integer> {

}
