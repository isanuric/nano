package com.isanuric.nano;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;

import com.isanuric.nano.dao.Artist;
import com.isanuric.nano.dao.ArtistRepository;
import com.isanuric.nano.dao.ArtistRepositoryService;
import com.mongodb.client.FindIterable;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArtistControllerTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistRepositoryService artistRepositoryService;


    private static void printUid(Document document) {
        System.out.println(document.getString("uid"));
    }

    @Test
    void crud() {
        artistRepository.deleteAll();
        this.createRandomAuthor();
        final List<Artist> underAge = artistRepositoryService.findByMaxAge(50);
        underAge.stream()
                .map(artist -> format("uid: %s, age: %d", artist.getUid(), artist.getAge()))
                .forEach(System.out::println);
    }

    public void createRandomAuthor() {
        Random random = new Random();
        range(0, 10).forEach(i -> {
            final var randomString = RandomStringUtils.random(5, true, false).toLowerCase();
            final var lastName = randomString + randomString;
            final var sequence = artistRepositoryService.generateUniqUid(randomString, Artist.SEQUENCE_NAME);
            final var artist = new Artist(sequence);
            artist.setFirstName(randomString);
            artist.setLastName(lastName);
            artist.setGenre(randomString + "-genre");
            artist.setEmail(randomString + "@gmail.com");
            artist.setSex("female");
            artist.setAge(random.nextInt(90));
            artistRepository.save(artist);
        });
    }

    @Test
    void findFemalesAll() {
        final FindIterable<Document> femalesAll = artistRepositoryService.findFemalesAll();
        femalesAll.forEach(ArtistControllerTest::printUid);
    }

    @Test
    void createDocument() {
        artistRepositoryService.createDocument("directors");
    }
}