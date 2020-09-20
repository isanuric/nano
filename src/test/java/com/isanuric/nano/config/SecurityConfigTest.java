package com.isanuric.nano.config;

import com.isanuric.nano.BaseTest;
import com.isanuric.nano.dao.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


class SecurityConfigTest extends BaseTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

//    @Test
//    void createAdmins() {
//        usersRepository.deleteAll();
//        usersRepository.save(new Users("admin01",passwordEncoder.encode("admin01")));
//        usersRepository.save(new Users("admin02",passwordEncoder.encode("admin02")));
//    }

    @Test
    void passwordEncoder() {
        final var password = passwordEncoder.encode("admin05");
//        usersRepository.save(new Users("admin05", password));
    }
}
