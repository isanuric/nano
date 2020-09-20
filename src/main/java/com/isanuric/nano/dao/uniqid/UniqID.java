package com.isanuric.nano.dao.uniqid;


import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.isanuric.nano.artist.Artist;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class UniqID {

    private final MongoOperations mongoOperations;

    @Autowired
    public UniqID(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public String generateUniqUid(String lastName, String seqName) {
        Artist counter = generateSequence(seqName);
        @NonNull final var counterPlus100 = Integer.parseInt(counter.getUid()) + 100;
        return lastName.toUpperCase() + counterPlus100;
    }

    private Artist generateSequence(String seqName) {
        return mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("uid", 1),
                options().returnNew(true).upsert(true),
                Artist.class);
    }

}
