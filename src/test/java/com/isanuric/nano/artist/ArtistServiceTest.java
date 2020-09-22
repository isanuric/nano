package com.isanuric.nano.artist;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.isanuric.nano.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class ArtistServiceTest extends BaseTest {

    @Autowired
    private ArtistService artistService;

    @Test
    void find_userNotExists() {
        assertThrows(IllegalArgumentException.class, () -> artistService.find("uid", "NOT_EXITS"));
    }

    @Test
    void findAllByEmail() {
        assertTrue(artistService.findAllByEmail("gmail.com").size() > 0);
    }

    @Test
    void findAllByUidsEmail() {
        assertTrue(artistService.findAllUidsByEmail("gmail.com").size() > 0);
    }

    @Test
    void findAllByGenre() {
        assertTrue(artistService.findAllByGenre("Magic realism").size() > 0);
    }

    @Test
    void findAllBySex() {
        assertTrue(artistService.findAllBySex("female").size() > 0);
    }

    @Test
    void findAllByCategory() {
        assertTrue(artistService.findAllByCategory("musician").size() > 0);
    }

    @Test
    void findAllByRole() {
        assertTrue(artistService.findAllByRole("USER").size() > 0);
    }
}
