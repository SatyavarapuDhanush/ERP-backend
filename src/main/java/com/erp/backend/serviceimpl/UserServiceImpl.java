package com.erp.backend.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.backend.exception.UserNotFoundException;
import com.erp.backend.model.UserModel;
import com.erp.backend.repository.UserRepository;
import com.erp.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository UJR;

    @Override
    public UserModel createUser(UserModel newuser) {
        UserModel user=UJR.save(newuser);
        return user;
    }

    @Override
    public List<UserModel> getAllUsers() {
    return UJR.findAll(); 
}


    @Override
    public UserModel getUserById(Long userId) {
        UserModel user=UJR.findByUserId(userId).orElseThrow(()-> new RuntimeException("User not found with ID: " + userId));
        return user;  
    }

    @Override
    public void deleteUser(Long userId) {
        UserModel user = UJR.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        UJR.delete(user);
    }

    @Override
    public UserModel updateUser(Long userId, UserModel selecteduser) {
        UserModel user=UJR.findByUserId(userId).orElseThrow(()-> new UserNotFoundException("User not found with ID: " + userId));
        if (selecteduser.getName() != null) {
            user.setName(selecteduser.getName());
        }
        if (selecteduser.getEmail() != null) {
            user.setEmail(selecteduser.getEmail());
        }
        if(selecteduser.getBranch()!=null){
            user.setBranch(selecteduser.getBranch());
        }
        if (selecteduser.getPassword() != null) {
            user.setPassword(selecteduser.getPassword());
        }
        return UJR.save(user);
    }   
}
