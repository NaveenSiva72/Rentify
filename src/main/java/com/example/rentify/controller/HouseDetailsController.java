package com.example.rentify.controller;

import com.example.rentify.domain.HouseDetailsWithDecodedPictures;
import com.example.rentify.model.HouseDetails;
import com.example.rentify.model.HousePictureDetails;
import com.example.rentify.repository.HouseDetailsRepository;
import com.example.rentify.service.HouseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/house")
public class HouseDetailsController {

    @Autowired
    HouseDetailsService houseDetailsService;

    @Autowired
    HouseDetailsRepository houseDetailsRepository;

    @PostMapping("/register")
    private ResponseEntity<HouseDetails> registerAHouse( @RequestPart("houseDetails") HouseDetails houseDetails , @RequestPart("files")  List<MultipartFile> multipartFiles) throws IOException {
        List<String> houseDetailPictureIds = new ArrayList<>();
        List<String> errorInUploading = new ArrayList<>();
        if(houseDetails.getId()==null)
        {
            houseDetails.setId(UUID.randomUUID());
        }
        for(MultipartFile multipartFile : multipartFiles)
        {
            try{
                houseDetailPictureIds.add(houseDetailsService.addImageToMongoDb(multipartFile));
            }
            catch (IOException e){
                errorInUploading.add(multipartFile.getOriginalFilename());
            }
        }
        houseDetails.setHousePicturesId(houseDetailPictureIds);
        System.out.println(errorInUploading);
        houseDetailsRepository.insert(houseDetails);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(houseDetails);
    }
    @GetMapping("getDetailsByEmail")
    private ResponseEntity<List<HouseDetailsWithDecodedPictures>> getHouseDetails(@RequestParam String email)
    {
        //Get all teh details of the house using emailId;
        List<HouseDetails> houseDetails = houseDetailsRepository.findByUserEmail(email);
        List<HouseDetailsWithDecodedPictures> houseDetailsWithDecodedPictures = new ArrayList<>();
        for(HouseDetails singleHouseDetails : houseDetails)
        {
            List<byte[]> decodedCurrentHousePictures = new ArrayList<>();
            //Iterate all the picturesId and get the decoded image from mongo
            for(String singleHousePictureId : singleHouseDetails.getHousePicturesId())
            {

                try {
                    decodedCurrentHousePictures.add(houseDetailsService.getHousePictures(singleHousePictureId ,singleHouseDetails ));
                } catch (IOException e) {
                    System.out.println("error in getting the image"+ singleHousePictureId);
                }

            }

            HouseDetailsWithDecodedPictures houseDetailsWithDecodedPictures1=new HouseDetailsWithDecodedPictures();
            houseDetailsWithDecodedPictures1.setHouseDetails(singleHouseDetails);
            houseDetailsWithDecodedPictures1.setDecodedHousePictures(decodedCurrentHousePictures);

            houseDetailsWithDecodedPictures.add(houseDetailsWithDecodedPictures1);
        }


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(houseDetailsWithDecodedPictures);
    }
}
