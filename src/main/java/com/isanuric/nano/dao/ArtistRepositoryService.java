package com.isanuric.nano.dao;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.set;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ArtistRepositoryService {

    private final MongoOperations mongoOperations;
    private final MongoTemplate mongoTemplate;

    private final MongoCollection<Document> artists;


    @Autowired
    public ArtistRepositoryService(MongoOperations mongoOperations,
            MongoTemplate mongoTemplate) {
        this.mongoOperations = mongoOperations;
        this.mongoTemplate = mongoTemplate;
        this.artists = mongoOperations.getCollection("artist");
    }

    public String generateUniqUid(String lastName, String seqName) {
        Artist counter = generateSequence(seqName);
        @NonNull final var counterPlus100 = Integer.parseInt(counter.getUid()) + 100;
        return counter != null ? lastName.toUpperCase() + counterPlus100 : lastName.toUpperCase() + 100;
    }

    private Artist generateSequence(String seqName) {
        return mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("uid", 1),
                options().returnNew(true).upsert(true),
                Artist.class);
    }

    public FindIterable<Document> findFemalesAll() {
        final MongoCollection<Document> artists = mongoOperations.getCollection("artist");
        return artists.find(Filters.in("sex", List.of("female")));
    }

    public void createDocument(String collectionName) {
        final MongoCollection<Document> directors = mongoOperations.getCollection(collectionName);
        List<String> movies = List.of("Lost Highway", "Elephantman", "Twin Peaks");
        Document lynch = new Document("uid", 1).append("name", "David Lynch").append("movies", movies);
        directors.insertOne(lynch);
    }

    public List<Artist> findByMaxAge(int age) {
        // return artists.find(Filters.lte("age", 50));
        return mongoOperations.find(new Query(Criteria.where("age").lte(age)), Artist.class);
    }

    public List<Artist> findAgeOver(int age) {
        Query query = new Query();
        query.fields().include("uid").include("age").exclude("id");
        query.addCriteria(Criteria.where("age").gt(age));
        return mongoTemplate.find(query, Artist.class);
    }

    public ArrayList<Document> find(String sex) {
        return artists.find(and(eq("sex", sex)))
                .projection(fields(excludeId(), include("uid", "sex", "age")))
                .sort(ascending("age"))
                .into(new ArrayList<>());
    }

    public ArrayList<Document> find(String sex, int age) {
        final Bson filter = Filters.and(eq("sex", sex), Filters.gte("age", age));
        return artists.find(filter)
                .projection(fields(excludeId(), include("uid", "sex", "age")))
                .sort(ascending("age"))
                .into(new ArrayList<>());
    }

    public void updateUser(String searchValue, String toChangeField, String newValue) {
        updateUser("uid", searchValue, toChangeField, newValue);
    }

    public UpdateResult updateUser(String searchField, String searchValue, String toChangeField, String toChangeValue) {
        Bson filter = eq(searchField, searchValue);
        Bson updateOperation = set(toChangeField, toChangeValue);
        return artists.updateOne(filter, updateOperation);
    }

    public Document findByUid(String uid) {
        Bson filter = eq("uid", uid);
        return artists.find(filter).first();
    }

    public ArrayList<Document> findByGenreGenderMinAge(String genre, String gender, int minAge) {
        Bson filter = Filters.and(eq("genre", genre), eq("sex", gender), gt("age", minAge));
        return artists.find(filter).into(new ArrayList<>());
    }

    public ArrayList<Document> findAll() {
        Bson filter = eq("uid");
        return artists.find().projection(fields(excludeId(), exclude("_class"))).into(new ArrayList<>());
    }
}
