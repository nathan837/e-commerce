package com.nathdev.e_commerce.service.user;

import org.springframework.stereotype.Service;

import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.User;
import com.nathdev.e_commerce.repository.UserRepository;
import com.nathdev.e_commerce.request.CreateUserRequest;
import com.nathdev.e_commerce.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

            private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        // TODO Auto-generated method stub
       return userRepository.findById(userId).orElseThrow(() 
       -> new ResourceNotFoundException("User no found"));
    }

    @Override
    public User createUser(CreateUserRequest user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        // TODO Auto-generated method stub
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));

    }

    @Override
    public void deleteUser(Long userId) {
        // TODO Auto-generated method stub
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, ()-> {
            throw new ResourceNotFoundException("User not found");
        });
    }
    
}
