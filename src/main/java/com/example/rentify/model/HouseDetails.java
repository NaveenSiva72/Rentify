package com.example.rentify.model;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.PrePersist;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document
public class HouseDetails {

    @Id
    UUID id;
    String houseName;
    double latitude ;
    double longitude;
    String houseType;
    long depositAmount;
    List<String> allowedGenders;
    String furnishedType;
    String availableFrom;

    UserDetails userDetails;

    List<String> housePictures;

}
