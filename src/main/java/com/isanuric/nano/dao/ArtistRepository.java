package com.isanuric.nano.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, Integer> {

    List<Artist> findByUid(String uid);

    List<Artist> findByLastName(String lastName);

    List<Artist> findByFirstName(String firstName);

    List<Artist> findByFirstNameLike(String firstName);

    List<Artist> findByGenre(String genre);

}
