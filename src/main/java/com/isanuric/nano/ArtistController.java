package com.isanuric.nano;


import com.isanuric.nano.dao.Artist;
import com.isanuric.nano.dao.ArtistRepository;
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

    public ArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Integer id) {
        final var result = this.artistRepository.findById(id);
        return "get";
    }

    @PostMapping("/add")
    public String add(@RequestBody Artist artist) {
        final var result = this.artistRepository.save(artist);
        return "";
    }

}
