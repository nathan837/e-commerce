package com.nathdev.e_commerce.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nathdev.e_commerce.DTO.ImageDto;
import com.nathdev.e_commerce.model.Image;

public interface IimageService {
  
  Image getImageById(Long id);
  void deleteImageById(Long id);
  List<ImageDto> saveImages(List<MultipartFile> files , Long productId);
  void updateImage(MultipartFile file , Long imageId);

}
