package com.erp.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.backend.model.UserModel;
import com.erp.backend.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin
@RequestMapping("api/auth/users")
public class UserController {
    
    @Autowired
    public UserService us;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserModel newUser) {
    try {
        UserModel user = us.createUser(newUser);
        String message = String.format("User created successfully. Welcome %s!", user.getName());
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>("An error occurred while creating the user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserModel>> getusers() {
    try {
        List<UserModel> users = us.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 if no users found
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserModel> getById(@PathVariable("userId") Long userId){
    try{
    UserModel user=us.getUserById(userId);
    return new ResponseEntity<>(user, HttpStatus.OK);
    }catch(Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId){
        try {
            us.deleteUser(userId);
            return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR); 
    }

}

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUserByDetails(@PathVariable("userId") Long userId,@RequestBody UserModel existinguser){
        try{
           UserModel updateduser=us.updateUser(userId, existinguser);
           String message=String.format("user updated successfully %s! ", updateduser.getName());
           return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); 
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the user.", HttpStatus.INTERNAL_SERVER_ERROR); 
    }
    }
}
