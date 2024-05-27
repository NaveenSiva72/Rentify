package com.example.rentify.domain;

import com.example.rentify.model.HouseDetails;
import lombok.Data;

import java.util.List;

@Data
public class HouseDetailsWithDecodedPictures {
    HouseDetails houseDetails;
    List<byte[]> decodedHousePictures;
}
