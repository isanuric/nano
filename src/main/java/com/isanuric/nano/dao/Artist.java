package com.isanuric.nano.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class Artist {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String uid;

    private String firstName;
    private String lastName;
    private String genre;
    private String email;
    private String sex;
    private String category;
    private int age;

    @Override
    public String toString() {
        return "Artist{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", genre='" + genre + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", category='" + category + '\'' +
                ", age=" + age +
                '}';
    }
}
