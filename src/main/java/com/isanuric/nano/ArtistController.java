package com.isanuric.nano;


import static org.springframework.util.Assert.notNull;

import com.isanuric.nano.dao.Artist;
import com.isanuric.nano.dao.ArtistAutoRepository;
import com.isanuric.nano.dao.ArtistRepository;
import com.isanuric.nano.dao.ArtistService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@ControllerAdvice
@RestController
@RequestMapping("/artist")
public class ArtistController {

    private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);

    private final ArtistAutoRepository artistAutoRepository;
    private final ArtistRepository artistRepository;
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistAutoRepository artistAutoRepository,
            ArtistRepository artistRepository, ArtistService artistService) {
        this.artistAutoRepository = artistAutoRepository;
        this.artistRepository = artistRepository;
        this.artistService = artistService;
    }

    @GetMapping("/{uid}")
    public Artist get(@Valid @Pattern(regexp = "^[a-zA-Z0-9]*$") @PathVariable String uid) {
        notNull(uid, "uid must not be empty");
        final Optional<Artist> artist;
        artist = this.artistService.findByUid(uid);
        logger.info("artist: [{}]", artist.toString());
        return artist.orElse(null);


    }

    @GetMapping("/all")
    public List<Artist> getAll() {
        return artistAutoRepository.findAll();
    }

    @GetMapping("/delete-all")
    public Object deleteAll() {
        artistAutoRepository.deleteAll();
        return artistAutoRepository.findAll().isEmpty() ? "success" : "Can not delete all entries";
    }

    @PostMapping("/add")
    public ResponseEntity<JSONObject> add(@Valid @RequestBody Artist artist) {
        final var saved = artistService.save(artist);

        final var responseHeaders = new HttpHeaders();
        responseHeaders.set("uid", saved.getUid());

        final var jsonObject = new JSONObject();
        jsonObject.put("uid", saved.getUid());
        jsonObject.put("description", "you can access to your data using uid: " + saved.getUid());

        return new ResponseEntity<>(jsonObject, responseHeaders, HttpStatus.CREATED);
    }
}
