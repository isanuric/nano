package com.isanuric.nano.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "database_sequences")
@Getter
@Setter
@Document
public class DatabaseSequence {

    @Id
    private String id;
    private long sequence;

}
