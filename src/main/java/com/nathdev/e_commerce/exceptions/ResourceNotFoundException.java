/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.nathdev.e_commerce.exceptions;

import com.nathdev.e_commerce.model.Category;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message_) {
        super("message_");
    }

}
