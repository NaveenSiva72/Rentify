package com.example.rentify.controller;


import com.example.rentify.model.UserDetails;
import com.example.rentify.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserManager {


    @Autowired
    UserDetailsRepository userDetailsRepository;

    @PostMapping("/register")
    private ResponseEntity<String> registerUser(@RequestBody UserDetails userDetails){
        String emailId = userDetails.getEmailId();
        try{

            Optional<UserDetails> temp=userDetailsRepository.findById(userDetails.getEmailId());
            if(temp.isPresent())
            {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(emailId +"This EmailId Already Exist");
            }

            userDetailsRepository.insert(userDetails);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(emailId +"Internal Server Error");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(emailId +" Registered Successfully");
    }

    @GetMapping("/login")
    private ResponseEntity<String> loginUser(@RequestParam String emailId,@RequestParam String password) {
        Optional<UserDetails> currentUserDetails = userDetailsRepository.findById(emailId);
        if (currentUserDetails.isPresent() && currentUserDetails.get().getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Successfully Authenticated");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("PLease verify your Email id or password");
    }
}
