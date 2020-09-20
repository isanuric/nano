package com.isanuric.nano.dao.user;

//import jakarta.validation.constraints.Pattern;

import javax.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    public String username;

    public String password;
    public String role;

}
