package com.isanuric.nano.dao;


import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@DataAmount
@Setter
@Getter
@AllArgsConstructor
public class Users {

    @Id
    public ObjectId _id;

    public String username;
    public String password;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
