package com.example.rentify.domain;


import com.example.rentify.model.HouseDetails;
import lombok.Data;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import java.util.List;

@Data
public class HouseDetailsWithDecodedPictures {
    private HouseDetails houseDetails;
    private List<GridFsResource> pictures;
}
