package com.nathdev.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nathdev.e_commerce.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

    


}
