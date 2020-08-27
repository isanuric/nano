package com.isanuric.nano;

import static java.util.stream.IntStream.range;

import com.isanuric.nano.dao.Artist;
import com.isanuric.nano.dao.ArtistRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArtistControllerTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Disabled
    @Test
    public void ceateRandomAuthor() {
        range(1, 10).forEach(i -> {
            final var random = RandomStringUtils.random(5, true, false).toUpperCase();
            final var artist = new Artist();
            artist.setFirstName(random);
            artist.setLastName(random + "-" + random);
            artist.setGenre("genre-" + random);
            artistRepository.save(artist);
        });
    }

    @Test
    void getAll() {
        final var all = artistRepository.findAll();
        for (Artist artist : all) {
            System.out.println(artist.getLastName());
        }
    }
}