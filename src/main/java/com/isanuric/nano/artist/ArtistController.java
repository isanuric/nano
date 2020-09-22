package com.isanuric.nano.artist;


import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.util.Assert.notNull;

import java.util.List;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);

    private final ArtistAutoRepository artistAutoRepository;
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistAutoRepository artistAutoRepository, ArtistService artistService) {
        this.artistAutoRepository = artistAutoRepository;
        this.artistService = artistService;
    }

    @GetMapping("/{uid}")
    public Artist get(@Valid @Pattern(regexp = "^[a-zA-Z0-9]*$") @PathVariable String uid) {
        checkArgument(!isNullOrEmpty(uid), "uid: %s must not be null", uid);
        return this.artistService.findByUid(uid);
    }

    @GetMapping("/genre/{genre}")
    public List<Artist> findByGenre(@Valid @Pattern(regexp = "^[a-zA-Z0-9]*$") @PathVariable String genre) {
        notNull(genre, "genre must not be empty");
        return this.artistService.findAllByGenre(genre);
    }

    @GetMapping("/genre-not/{genre}")
    public List<JSONObject> findByGenreNotEqual(@Valid @Pattern(regexp = "^[a-zA-Z0-9]*$") @PathVariable String genre) {
        notNull(genre, "genre must not be empty");
        return this.artistService.findByGenreNotEqual(genre);
    }

//    @GetMapping("/age-over/{age}")
//    public List<Artist> findByAgesOver(@Valid @Pattern(regexp = "^[a-zA-Z0-9]*$") @PathVariable Integer age) {
//        notNull(age, "age must not be empty");
//        return this.artistService.findByAgesOver(age);
//    }

    @PatchMapping("/update/{uid}")
    public Artist getTest(
            @Valid @Pattern(regexp = "^[a-zA-Z0-9]*$") @PathVariable String uid,
            @Valid @RequestBody Artist artist) {
        checkArgument(uid != null, "uid: %s must not be null", uid);
        return this.artistService.updateValue(uid, "email", "new@gmail.com");
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
