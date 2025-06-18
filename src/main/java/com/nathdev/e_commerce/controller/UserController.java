package com.nathdev.e_commerce.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nathdev.e_commerce.DTO.UserDto;
import com.nathdev.e_commerce.exceptions.AlreadyExistsException;
import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.User;
import com.nathdev.e_commerce.request.CreateUserRequest;
import com.nathdev.e_commerce.request.UserUpdateRequest;
import com.nathdev.e_commerce.response.ApiResponse;
import com.nathdev.e_commerce.service.user.IUserService;
import com.nathdev.e_commerce.service.user.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    
    private final IUserService userService;
    

 @GetMapping("/{userId}/user")
 public ResponseEntity<ApiResponse>getUserById(@pathvariable Long id){
 try{
    User user = userService.getUserById(id);
    UserDto userDto = userService.convertUserToDto(user);
   return ResponseEntity.ok( new ApiResponse("SUCCESS", userDto));
 }catch(ResourceNotFoundException e){
    return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
 }
}
  @PostMapping("/add")
 public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
    try{
    User user = userService.createUser(request);
    UserDto userDto = userService.convertUserToDto(user);
    return ResponseEntity.ok( new ApiResponse("Create User Success", userDto));
    }catch(AlreadyExistsException e){
        return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
    }
 }
 @PutMapping("/{userId}/update")
 public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request , @pathvariable Long userId){
    try{
        User user = userService.updateUser(request, userId);
        UserDto userDto = userService.convertUserToDto(user);
    return ResponseEntity.ok(new ApiResponse("Update User Success", userDto));
    }catch(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
 }
 @DeleteMapping("/{userId}/delete")
   public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
      try{
        UserService userService1 = new UserService(null, null);
        userService1.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("Delete User Sucess",null));
      }catch(ResourceNotFoundException e){
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
  } 
}
}