package com.isanuric.nano;


import com.isanuric.nano.dao.Artist;
import com.isanuric.nano.dao.ArtistRepository;
import com.isanuric.nano.dao.ArtistRepositoryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    private ArtistRepository artistRepository;
    private ArtistRepositoryService artistRepositoryService;

    @Autowired
    public ArtistController(
            ArtistRepository artistRepository,
            ArtistRepositoryService artistRepositoryService) {
        this.artistRepository = artistRepository;
        this.artistRepositoryService = artistRepositoryService;
    }

    public ArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @GetMapping("/{uid}")
    public Artist get(@PathVariable Long uid) {
        final var artist = this.artistRepository.findByUid(uid);
        return !ObjectUtils.isEmpty(artist) ? artist.get(0) : new Artist(null);
    }

    @GetMapping("/all")
    public List<Artist> getAll() {
        return artistRepository.findAll();
    }

    @GetMapping("/delete-all")
    public Object deleteAll() {
        artistRepository.deleteAll();
        return artistRepository.findAll().isEmpty() ? "success" : "Can not delete all entries";
    }

    @PostMapping("/add")
    public Map<String, String> add(@RequestBody Artist artist) {
        artist.setUid(artistRepositoryService.generateSequence(artist.getLastName(), Artist.SEQUENCE_NAME));
        @NonNull final var uid = this.artistRepository.save(artist).getUid();

        HashMap<String, String> map = new HashMap<>();
        map.put("result", "success");
        map.put("uid", String.valueOf(uid));
        map.put("description", "you can access to your data using uid [" + uid + "]");
        return map;
    }
}
