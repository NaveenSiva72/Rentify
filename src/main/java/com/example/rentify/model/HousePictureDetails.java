package com.example.rentify.model;

import lombok.Data;

import java.util.List;

@Data
public class HousePictureDetails {
    HouseDetails houseDetails;
    List<byte[]> file;
}
