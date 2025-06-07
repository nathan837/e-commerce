package com.nathdev.e_commerce.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message_) {
        super(message_);
    }
    
}
