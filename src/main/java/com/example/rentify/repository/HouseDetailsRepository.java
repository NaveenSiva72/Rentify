package com.example.rentify.repository;

import com.example.rentify.model.HouseDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HouseDetailsRepository extends MongoRepository<HouseDetails , UUID> {
    @Query("{ 'userDetails._id': ?0 }")
    List<HouseDetails> findByUserEmail(String email);
}
