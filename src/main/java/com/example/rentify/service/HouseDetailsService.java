package com.example.rentify.service;
import com.example.rentify.model.HouseDetails;
import com.example.rentify.repository.HouseDetailsRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HouseDetailsService {

    @Autowired
    private HouseDetailsRepository houseDetailsRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public HouseDetails saveHouseDetails(HouseDetails houseDetails, List<MultipartFile> files) throws IOException {
        if(houseDetails.getId()==null)
        {
            houseDetails.setId(UUID.randomUUID());
        }
        List<String> pictureIds = files.stream()
                .map(file -> {
                    try {
                        return gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType()).toString();
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to store file", e);
                    }
                })
                .collect(Collectors.toList());
        houseDetails.setHousePictures(pictureIds);
        return houseDetailsRepository.insert(houseDetails);
    }

    public List<HouseDetails> getAllHouseDetailsByEmail(String email) {
        return houseDetailsRepository.findByEmail(email);
    }

    public GridFSFile getPictureById(String id) {
        return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
    }

    public GridFsResource getPictureResource(GridFSFile gridFSFile) {
        return gridFsTemplate.getResource(gridFSFile);
    }

    public ArrayList<GridFSFile> getPicturesByHouseDetailId(List<String> houseDetailId) {
        return gridFsTemplate.find(new Query(Criteria.where("metadata.houseDetailId").is(houseDetailId))).into(new ArrayList<>());
    }


}
