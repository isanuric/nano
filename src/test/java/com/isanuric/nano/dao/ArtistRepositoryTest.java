package com.isanuric.nano.dao;

import com.isanuric.nano.BaseTest;
import com.isanuric.nano.artist.Artist;
import com.isanuric.nano.artist.ArtistRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ArtistRepositoryTest extends BaseTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    void findAgeOver18() {
        final List<Artist> ageOver = artistRepository.findAgeOver(18);
        ageOver.forEach(System.out::println);
    }

//    @Test
//    void findArtist() {
//        final var artist = artistRepository.find("uid", "uidTester");
//        System.out.println("-> " + artist);
//    }

}