package com.isanuric.nano.artist;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.set;

import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class DocumentRepositoryService {

    private final MongoCollection<Document> document;
    private final MongoOperations mongoOperations;

    @Autowired
    public DocumentRepositoryService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
        this.document = mongoOperations.getCollection("artist");
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

    public ArrayList<Document> find(String sex) {
        return document.find(and(eq("sex", sex)))
                .projection(fields(excludeId(), include("uid", "sex", "age")))
                .sort(ascending("age"))
                .into(new ArrayList<>());
    }

    public ArrayList<Document> find(String sex, int age) {
        final Bson filter = Filters.and(eq("sex", sex), Filters.gte("age", age));
        return document.find(filter)
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
        return document.updateOne(filter, updateOperation);
    }

    public Document findByUid(String uid) {
        Bson filter = eq("uid", uid);
        return document.find(filter).first();
    }

    public ArrayList<Document> findByGenreGenderMinAge(String genre, String gender, int minAge) {
        Bson filter = Filters.and(eq("genre", genre), eq("sex", gender), gt("age", minAge));
        return document.find(filter).into(new ArrayList<>());
    }

    public ArrayList<Document> findAll() {
        return document.find().projection(fields(excludeId(), exclude("_class"))).into(new ArrayList<>());
    }

    public ArrayList<Document> findAllUids() {
        return document.find().projection(fields(excludeId(), include("uid")))
                .sort(ascending("uid"))
                .into(new ArrayList<>());
    }

    public ListIndexesIterable<Document> getIndexing() {
//        IndexOptions indexOptions = new IndexOptions().name("uid").unique(true);
        return document.listIndexes();
    }
}
