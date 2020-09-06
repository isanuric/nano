package com.isanuric.nano;


import static java.util.Arrays.asList;

import com.isanuric.nano.dao.Users;
import com.isanuric.nano.dao.UsersRepository;
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

    private final UsersRepository usersRepository;

    @Autowired
    public NanoUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);
        logger.info("username:password [{}]:[{}], Authentication [{}]", user.getUsername(), user.getPassword());
        if (user == null) {
            logger.info("User [{}] not found.", username);
            throw new UsernameNotFoundException("User [%s] not found." + username);
        }
        List<SimpleGrantedAuthority> authorities = asList(new SimpleGrantedAuthority("user"));
        logger.info("username:password [{}]:[{}], Authentication [{}]", user.getUsername(), user.getPassword(),
                authorities);
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
