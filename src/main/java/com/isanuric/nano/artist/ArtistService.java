package com.isanuric.nano.artist;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.isanuric.nano.dao.uniqid.UniqID;
import com.isanuric.nano.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private final UniqID uniqID;
    private final PasswordEncoder passwordEncoder;
    private final ArtistRepository artistRepository;

    public ArtistService(UniqID uniqID, PasswordEncoder passwordEncoder, ArtistRepository artistRepository) {
        this.uniqID = uniqID;
        this.passwordEncoder = passwordEncoder;
        this.artistRepository = artistRepository;
    }

    public Artist save(Artist artist) {
        artist.setUid(uniqID.generateUniqUid(artist.getLastName(), Artist.SEQUENCE_NAME));
        artist.setPassword(passwordEncoder.encode(artist.getPassword()));
        return artistRepository.save(artist);
    }

    public Artist findByUid(String uid) {
        return this.find("uid", uid).get(0);
    }

    public List<Artist> findByGenre(String genre) {
        return this.find("genre", genre);
    }

    public List<Artist> find(String key, String value) {
        final var artists = artistRepository.find(createQuery(key, value));
        if (isEmpty(artists)) {
            throw new EntityNotFoundException(format("uid %s not found", value));
        }
        return artists;
    }

    private Query createQuery(String key, String value) {
        final var query = getQuery();
        query.addCriteria(Criteria.where(key).is(value));
        return query;
    }


    public List<JSONObject> findByGenreNotEqual(String value) {
        final var query = getQuery();
        query.addCriteria(Criteria.where("genre").ne(value));
        final var artists = artistRepository.find(query);
        final List<JSONObject> jsonObject = new ArrayList<>();
        artists.forEach(artist -> {
            final var json = new JSONObject();
            json.put("uid", artist.getUid());
            json.put("genre", artist.getGenre());
            jsonObject.add(json);
        });
        return jsonObject;
    }

    private Query getQuery() {
        return new Query();
    }

}
