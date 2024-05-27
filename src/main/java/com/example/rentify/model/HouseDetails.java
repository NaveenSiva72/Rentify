package com.example.rentify.model;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document
public class HouseDetails {

    @Id
    UUID id;
//    List<HousePictureDetails> housePictureDetails;
    List<String> housePicturesId;
    private  UserDetails userDetails;

}
