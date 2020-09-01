package com.isanuric.nano.dao;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mongodb.client.FindIterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Lists;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArtistRepositoryServiceTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistRepositoryService artistService;

    private JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
    private Random random = new Random();

    private static Consumer<Document> print = new Consumer<Document>() {
        @Override
        public void accept(final Document document) {
            System.out.println(document.toJson());
        }
    };


    @Test
    void populateDB() {
        artistRepository.deleteAll();
        this.createRandomArtist();
    }

    private void createRandomArtist() {
        List<String> geners = Lists.newArrayList("Dada", "Installation art", "Magic realism", "Analytical art");
        List<String> email = Lists.newArrayList("@gmail.com", "@gmx.de", "@yahoo.com", "@university.fr");
        List<String> category = Lists.newArrayList("actor", "painter", "musician", "author", "dancer");
        List<String> sex = Lists.newArrayList("female", "male", "transgender");
        range(0, 30).forEach(i -> {
            final var randomString = RandomStringUtils.random(5, true, false).toLowerCase();
            final var sequence = artistService.generateUniqUid(randomString, Artist.SEQUENCE_NAME);
            final var lastName = randomString + randomString;

            final var artist = new Artist(sequence);
            artist.setAge(random.nextInt(90));
            artist.setFirstName(randomString);
            artist.setLastName(lastName);

            artist.setEmail(randomString + email.get(random.nextInt(email.size())));
            artist.setCategory(category.get(random.nextInt(category.size())));
            artist.setGenre(geners.get(random.nextInt(geners.size())));
            artist.setSex(sex.get(random.nextInt(sex.size())));
            artistRepository.save(artist);
        });
        artistRepository.save(new Artist("uidTester"));
    }

    @Test
    void findFemalesAll() {
        final FindIterable<Document> femalesAll = artistService.findFemalesAll();
        femalesAll.forEach(this::printUid);
    }

    private void printUid(Document document) {
        System.out.println(document.getString("uid"));
    }

    @Test
    void findByMaxAge() {
        final List<Artist> underAge = artistService.findByMaxAge(50);
        underAge.stream()
                .map(artist -> format("uid: %s, age: %d", artist.getUid(), artist.getAge()))
                .forEach(System.out::println);
    }

    @Test
    void createDocument() {
        artistService.createDocument("directors");
    }

    @Test
    void findAgeOver18() {
        final List<Artist> ageOver = artistService.findAgeOver(18);
        ageOver.forEach(System.out::println);
    }

    @Test
    void findByGender() {
        final ArrayList<Document> indexing = artistService.find("transgender");
        indexing.forEach(print);
    }

    @Test
    void findByGenderAndAge() {
        final ArrayList<Document> indexing = artistService.find("male", 20);
        indexing.forEach(print);
    }

    @Test
    void updateUser() {
        range(0, 10).mapToObj(v -> valueOf(random.nextInt(100))).forEach(randomValue -> {
            artistService.updateUser("uidTester", "age", randomValue);
            assertEquals(true, artistService.findByUid("uidTester").toJson().contains(randomValue));
        });
    }

    @Test
    void findByGenreGenderMinAge() {
        artistService.findByGenreGenderMinAge("Dada", "female", 20).forEach(print);
    }

    @Test
    void findAll() {
        artistService.findAll().forEach(print);
    }

    @Test
    void findAllUids() {
        artistService.findAllUids().forEach(print);
    }

    @Test
    void getIndexing() {
        System.out.println();
        final ArrayList<Document> into = artistService.getIndexing().into(new ArrayList<>());
        into.forEach(System.out::println);
    }


}