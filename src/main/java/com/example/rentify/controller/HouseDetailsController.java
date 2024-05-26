package com.example.rentify.controller;

import com.example.rentify.domain.HouseDetailsWithDecodedPictures;
import com.example.rentify.model.HouseDetails;
import com.example.rentify.service.HouseDetailsService;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/house")
public class HouseDetailsController {

    @Autowired
    HouseDetailsService houseDetailsService;

    @PostMapping("/register")
    public ResponseEntity<HouseDetails> createHouseDetails(
            @RequestPart("houseDetails") HouseDetails houseDetails,
            @RequestPart("files") List<MultipartFile> files) {
        try {
            HouseDetails savedHouseDetails = houseDetailsService.saveHouseDetails(houseDetails, files);
            return new ResponseEntity<>(savedHouseDetails, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDetails")
    public ResponseEntity<List<HouseDetailsWithDecodedPictures>> getAllHouseDetailsByEmail(@RequestParam String email) throws IOException {
        List<HouseDetails> houseDetailsList = houseDetailsService.getAllHouseDetailsByEmail(email);
        List<HouseDetailsWithDecodedPictures> HouseDetailsWithDecodedPicturesList = new ArrayList<>();

        for (HouseDetails houseDetails : houseDetailsList) {
            List<GridFsResource> pictureResources = new ArrayList<>();
            ArrayList<GridFSFile> pictureFiles = houseDetailsService.getPicturesByHouseDetailId(houseDetails.getHousePictures());

            for (GridFSFile pictureFile : pictureFiles) {
                pictureResources.add(houseDetailsService.getPictureResource(pictureFile));
            }

            HouseDetailsWithDecodedPictures houseDetailsWithDecodedPictures = new HouseDetailsWithDecodedPictures();
            houseDetailsWithDecodedPictures.setHouseDetails(houseDetails);
            houseDetailsWithDecodedPictures.setPictures(pictureResources);

            HouseDetailsWithDecodedPicturesList.add(houseDetailsWithDecodedPictures);
        }

        return new ResponseEntity<>(HouseDetailsWithDecodedPicturesList, HttpStatus.OK);
    }
}
