package com.isanuric.nano.artist;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistAutoRepository extends MongoRepository<Artist, Integer> {

    Artist findByUid(String uid);

    List<Artist> findByLastName(String lastName);

    List<Artist> findByFirstName(String firstName);

    List<Artist> findByFirstNameLike(String firstName);

    List<Artist> findByGenre(String genre);

}
