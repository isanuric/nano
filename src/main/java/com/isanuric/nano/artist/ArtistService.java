package com.isanuric.nano.artist;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.isanuric.nano.dao.uniqid.UniqID;
import java.util.ArrayList;
import java.util.List;
import net.minidev.json.JSONObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    public List<Artist> findAllByGenre(String genre) {
        return this.find("genre", genre);
    }

    public List<Artist> findAllBySex(String sex) {
        return this.find("sex", sex);
    }

    public List<Artist> findAllByCategory(String category) {
        return this.find("category", category);
    }

    public List<Artist> findAllByEmail(String email) {
        return artistRepository.find(query(Criteria.where("email").regex(email)));
    }

    public List<Artist> find(String key, String value) {
        final var artists = artistRepository.find(createQuery(key, value));
        checkArgument(!isEmpty(artists), "uid %s not found", value);
        return artists;
    }

    private Query createQuery(String key, String value) {
        return query(Criteria.where(key).is(value));
    }

    public List<JSONObject> findByGenreNotEqual(String value) {
        final var artists = artistRepository.find(query(Criteria.where("genre").ne(value)));

        final var jsonObject = new ArrayList<JSONObject>();
        artists.forEach(artist -> {
            final var json = new JSONObject();
            json.put("uid", artist.getUid());
            json.put("genre", artist.getGenre());
            jsonObject.add(json);
        });
        return jsonObject;
    }

    public Artist updateValue(String uid, String key, String value) {
//        final var query = getQuery();
//        query.fields().include("uid").include("age").exclude("id");
//        query.addCriteria(Criteria.where("uid").gt(uid));
//        artistRepository.findAndReplace(query, "aaa");

        final Query query = query(where("uid").is(uid));
        query.fields().exclude("id");
        final Update update = update(key, value);
        artistRepository.update(query, update);
        return this.findByUid(uid);
    }
}
