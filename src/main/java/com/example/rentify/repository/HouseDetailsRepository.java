package com.example.rentify.repository;

import com.example.rentify.model.HouseDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HouseDetailsRepository extends MongoRepository<HouseDetails , UUID> {

    @Query("{ 'userDetails._id': ?0 }")
    List<HouseDetails> findByEmail(String email);
}
