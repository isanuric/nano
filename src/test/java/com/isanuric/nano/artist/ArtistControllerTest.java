package com.isanuric.nano.artist;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Base64Utils;


@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ArtistControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    private static final String AUTHENTICATION = "Basic " + Base64Utils.encodeToString(("userOne:testpass").getBytes());

    @Test
    void get() {
        final var authentication = "Basic " + Base64Utils.encodeToString(("userOne:testpass").getBytes());
        webTestClient.get().uri("artist/all")
                .header(HttpHeaders.AUTHORIZATION, authentication)
                .exchange()
                .expectStatus().isOk();
    }

    @Disabled
    @Test
    void add() {
        webTestClient.post().uri("/artist/add")
                .header(HttpHeaders.AUTHORIZATION, AUTHENTICATION)
                .exchange()
                .expectStatus().isOk();
    }
}