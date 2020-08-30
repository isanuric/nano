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
import org.assertj.core.util.Lists;
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
    void updateDB() {
        artistRepository.deleteAll();
        this.createRandomArtist();
    }

    private void createRandomArtist() {
        Random random = new Random();
        List<String> category = Lists.newArrayList("actor", "painter", "musician", "author", "dancer");
        List<String> sex = Lists.newArrayList("female", "male", "transgender");
        List<String> email = Lists.newArrayList("@gmail.com", "@gmx.de", "@yahoo.com", "@university.fr");
        range(0, 10).forEach(i -> {
            final var randomString = RandomStringUtils.random(5, true, false).toLowerCase();
            final var sequence = artistRepositoryService.generateUniqUid(randomString, Artist.SEQUENCE_NAME);
            final var lastName = randomString + randomString;

            final var artist = new Artist(sequence);
            artist.setGenre(randomString + "-genre");
            artist.setAge(random.nextInt(90));
            artist.setFirstName(randomString);
            artist.setLastName(lastName);

            artist.setEmail(randomString + email.get(random.nextInt(email.size())));
            artist.setCategory(category.get(random.nextInt(category.size())));
            artist.setSex(sex.get(random.nextInt(sex.size())));
            artistRepository.save(artist);
        });
    }

    @Test
    void findFemalesAll() {
        final FindIterable<Document> femalesAll = artistRepositoryService.findFemalesAll();
        femalesAll.forEach(ArtistControllerTest::printUid);
    }

    @Test
    void findByMaxAge() {
        final List<Artist> underAge = artistRepositoryService.findByMaxAge(50);
        underAge.stream()
                .map(artist -> format("uid: %s, age: %d", artist.getUid(), artist.getAge()))
                .forEach(System.out::println);
    }

    @Test
    void createDocument() {
        artistRepositoryService.createDocument("directors");
    }

    @Test
    void findAgeOver18() {
        final List<Artist> ageOver = artistRepositoryService.findAgeOver(18);
        ageOver.forEach(System.out::println);
    }

    @Test
    void indexing() {
        artistRepositoryService.indexing();
    }
}