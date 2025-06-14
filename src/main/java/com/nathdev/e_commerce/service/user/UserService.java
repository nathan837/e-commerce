package com.nathdev.e_commerce.service.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nathdev.e_commerce.exceptions.AlreadyExistsException;
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
    public User createUser(CreateUserRequest request) {
        // TODO Auto-generated method stub
        return Optional.of(request).filter(user ->  !userRepository.existsByEmail(request.getEmail()))
        .map(req -> {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            return userRepository.save(user);
        }).orElseThrow(() -> new AlreadyExistsException("Oops!" + request.getEmail() + "already Exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        // TODO Auto-generated method stub
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
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
