package com.nathdev.e_commerce.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class shopConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}