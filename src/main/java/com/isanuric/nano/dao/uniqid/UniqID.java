package com.isanuric.nano.dao.uniqid;


import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.util.StringUtils.*;

import com.isanuric.nano.artist.Artist;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UniqID {

    private final MongoOperations mongoOperations;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UniqID(MongoOperations mongoOperations,
            PasswordEncoder passwordEncoder) {
        this.mongoOperations = mongoOperations;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateUniqId(String lastName, String seqName) {
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

    public String encodePassword(String password) {
        checkArgument(!isEmpty(password), "password must not be empty");
        return passwordEncoder.encode(password);
    }

}
