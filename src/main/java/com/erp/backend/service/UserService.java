package com.erp.backend.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.backend.model.UserModel;

@Service
public interface UserService {
    public UserModel createUser(UserModel newuser);
    public List<UserModel> getAllUsers();
    public UserModel getUserById(Long userId);
    public UserModel updateUser(Long userId,UserModel selecteduser);
    public void deleteUser(Long userId);
}
