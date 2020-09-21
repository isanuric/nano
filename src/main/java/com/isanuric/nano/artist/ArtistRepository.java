package com.isanuric.nano.artist;


import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.client.result.UpdateResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ArtistRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ArtistRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Artist> findAgeOver(int age) {
        Query query = new Query();
        query.fields().include("uid").include("age").exclude("id");
        query.addCriteria(where("age").gt(age));
        return mongoTemplate.find(query, Artist.class);
    }

    public List<Artist> find(Query query) {
        return mongoTemplate.find(query, Artist.class);
    }

    public UpdateResult update(Query query, Update update) {
        return mongoTemplate.updateFirst(query, update, Artist.class);
    }

    public Artist save(Artist artist) {
        return mongoTemplate.save(artist);
    }

}
