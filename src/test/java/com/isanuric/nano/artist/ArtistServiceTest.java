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
        final var allByEmail = artistService.findAllByEmail("gmail.com");
        assertTrue(allByEmail.size() > 0);
    }
}
