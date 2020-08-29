package com.isanuric.nano.dao;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import lombok.NonNull;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ArtistRepositoryService {

    private final MongoOperations mongoOperations;

    public ArtistRepositoryService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public String generateSequence(String lastName, String seqName) {
        Artist counter = mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("uid", 1),
                options().returnNew(true).upsert(true),
                Artist.class);
        @NonNull final var counterPlus100 = Integer.parseInt(counter.getUid()) + 100;
        return counter != null ? lastName.toUpperCase() + counterPlus100 : lastName.toUpperCase() + 100;
    }
}
