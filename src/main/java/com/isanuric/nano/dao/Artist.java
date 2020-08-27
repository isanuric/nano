package com.isanuric.nano.dao;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@NoArgsConstructor
public class Artist {

    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String genre;
}
