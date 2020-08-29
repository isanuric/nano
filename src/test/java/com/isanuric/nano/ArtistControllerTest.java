package com.isanuric.nano;

import static java.util.stream.IntStream.range;

import com.isanuric.nano.dao.Artist;
import com.isanuric.nano.dao.ArtistRepository;
import com.isanuric.nano.dao.ArtistRepositoryService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArtistControllerTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistRepositoryService artistRepositoryService;


    @Test
    public void ceateRandomAuthor() {
        range(0, 2).forEach(i -> {
            final var random = RandomStringUtils.random(5, true, false).toUpperCase();
            final var lastName = random + random;
            final var sequence = artistRepositoryService.generateSequence(lastName, Artist.SEQUENCE_NAME);
            final var artist = new Artist(sequence);
            artist.setFirstName(random);
            artist.setLastName(lastName);
            artist.setGenre(random + "-genre");
            artistRepository.save(artist);
        });
    }
}