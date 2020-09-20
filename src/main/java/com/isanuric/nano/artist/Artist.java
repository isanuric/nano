package com.isanuric.nano.artist;

//import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    private static final String ALPHANUMERIC = "^[a-zA-Z0-9_]*$";

    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String uid;

    @JsonIgnore
    @NonNull
    @Size(min = 6, message = "Password length must be at least 6")
    private String password;

    private String role;

    @Pattern(regexp = ALPHANUMERIC)
    private String firstName;

    @Pattern(regexp = ALPHANUMERIC)
    private String lastName;

    @Pattern(regexp = ALPHANUMERIC)
    private String genre;
    private String email;

    @Pattern(regexp = ALPHANUMERIC)
    private String sex;

    @Pattern(regexp = ALPHANUMERIC)
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
