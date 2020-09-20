package com.isanuric.nano.dao;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        query.addCriteria(Criteria.where("age").gt(age));
        return mongoTemplate.find(query, Artist.class);
    }

//    public Artist findByUid(String uid) {
////        Assert.notNull(find("uid", uid), "uid not found");
//        return find("uid", uid).get(0);
//    }

    public List<Artist> find(Query query) {
        return mongoTemplate.find(query, Artist.class);
    }

    public Artist save(Artist artist) {
        return mongoTemplate.save(artist);
    }

}
