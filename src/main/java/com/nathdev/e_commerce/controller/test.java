package com.nathdev.e_commerce.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {
      public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    System.out.println(encoder.encode(" "));  // Replace with your user's real password
}
}
