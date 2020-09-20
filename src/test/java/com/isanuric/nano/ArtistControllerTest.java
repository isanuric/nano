package com.isanuric.nano;

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

    @Test
    void get() {
        final var authentication = "Basic " + Base64Utils.encodeToString(("EHZQW101:testpass").getBytes());
        webTestClient.get().uri("artist/all")
                .header(HttpHeaders.AUTHORIZATION, authentication)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void add() {
        final var auth = "Basic " + Base64Utils.encodeToString(("EHZQW101:testpass").getBytes());
        webTestClient.post().uri("/artist/add")
                .header(HttpHeaders.AUTHORIZATION, auth)
                .exchange()
                .expectStatus().isOk();
    }
}