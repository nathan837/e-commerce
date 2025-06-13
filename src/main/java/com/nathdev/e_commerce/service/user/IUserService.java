package com.nathdev.e_commerce.service.user;

import com.nathdev.e_commerce.model.User;
import com.nathdev.e_commerce.request.CreateUserRequest;
import com.nathdev.e_commerce.request.UserUpdateRequest;

public interface IUserService {

    
    User getUserById(Long userId);
    User createUser (CreateUserRequest user);
    User updateUser(UserUpdateRequest request , Long userId );
    void deleteUser(Long userId);

}
