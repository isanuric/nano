package com.isanuric.nano.config;


import static java.util.Arrays.asList;

import com.isanuric.nano.artist.Artist;
import com.isanuric.nano.artist.ArtistAutoRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NanoUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(NanoUserDetailsService.class);

    private final ArtistAutoRepository artistAutoRepository;

    @Autowired
    public NanoUserDetailsService(ArtistAutoRepository artistAutoRepository) {
        this.artistAutoRepository = artistAutoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Artist artis = artistAutoRepository.findByUid(username);
        if (artis == null) {
            logger.info("User [{}] not found.", username);
            throw new UsernameNotFoundException("User [%s] not found." + username);
        }
        List<SimpleGrantedAuthority> authorities = asList(new SimpleGrantedAuthority(artis.getRole()));
        logger.info("username [{}], authorities [{}]", artis.getUid(), authorities);
        return new User(artis.getUid(), artis.getPassword(), authorities);
    }
}
