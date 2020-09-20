package com.isanuric.nano.dao;

import static java.util.Optional.ofNullable;
import static org.springframework.util.ObjectUtils.isEmpty;

import com.isanuric.nano.exception.EntityNotFoundException;
import java.util.Optional;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private final UniqID uniqID;
    private final PasswordEncoder passwordEncoder;
    private final ArtistRepository artistRepository;

    public ArtistService(UniqID uniqID, PasswordEncoder passwordEncoder,
            ArtistRepository artistRepository) {
        this.uniqID = uniqID;
        this.passwordEncoder = passwordEncoder;
        this.artistRepository = artistRepository;
    }

    public Artist save(Artist artist) {
        artist.setUid(uniqID.generateUniqUid(artist.getLastName(), Artist.SEQUENCE_NAME));
        artist.setPassword(passwordEncoder.encode(artist.getPassword()));
        return artistRepository.save(artist);
    }

    public Optional<Artist> findByUid(String uid) {
        final var artists = artistRepository.find(createQuery("uid", uid));
        if (isEmpty(artists)) {
            throw new EntityNotFoundException(uid);
        }
        return ofNullable(artists.get(0));
    }

    private Query createQuery(String key, String value) {
        Query query = new Query();
        query.addCriteria(Criteria.where(key).is(value));
        return query;
    }
}
