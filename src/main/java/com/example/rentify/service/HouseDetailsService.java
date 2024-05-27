package com.example.rentify.service;

import com.example.rentify.model.HouseDetails;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class HouseDetailsService {

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;



    public String addImageToMongoDb(MultipartFile currentFile) throws IOException {


        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", currentFile.getSize());

        Object fileID = template.store(currentFile.getInputStream(), currentFile.getOriginalFilename(), currentFile.getContentType(), metadata);

        return fileID.toString();
    }


//    public HouseDetails downloadFile(String id) throws IOException {
//
//        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );
//
//        HouseDetails houseDetails = new HouseDetails();
//
//        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
//            loadFile.setFilename( gridFSFile.getFilename() );
//
//            loadFile.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );
//
//            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );
//
//            loadFile.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
//        }
//
//        return loadFile;
//    }

    public byte[] getHousePictures(String singleHousePictureId,HouseDetails singleHouseDetail) throws IOException {
        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(singleHousePictureId)) );

        if(gridFSFile==null)
        {
            return null;
        }
        return  IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream());
    }
}
