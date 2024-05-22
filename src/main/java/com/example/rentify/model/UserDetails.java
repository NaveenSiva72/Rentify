package com.example.rentify.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class UserDetails {

    @Id
    @Indexed(unique = true)
    String emailId;

    String firstName;

    String lastName;

    @Indexed(unique = true)
    String phoneNo;

    String password;

}
