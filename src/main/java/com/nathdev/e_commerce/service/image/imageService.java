package com.nathdev.e_commerce.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nathdev.e_commerce.DTO.ImageDto;
import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.Image;
import com.nathdev.e_commerce.model.Product;
import com.nathdev.e_commerce.repository.ImageRepository;
import com.nathdev.e_commerce.service.product.I_ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class imageService implements IimageService {
               private final ImageRepository imageRepository;
               private final I_ProductService productService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("No image found id " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
         () -> {throw new ResourceNotFoundException("No image found with id " + id);});
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product =productService.getProductById(productId);
          
        List<ImageDto> savedeImageDto= new ArrayList<>();
        for(MultipartFile file: files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                
              String buildDownloadUrl = "/api/v1/images/image/download/";

              String downloadUrl = buildDownloadUrl + image.getId();
              image.setDownloadUrl(downloadUrl);
             Image savedImage =  imageRepository.save(image);

             savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
               imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedeImageDto.add(imageDto);
                

            } catch (IOException | SQLException e) {
                throw new RuntimeException (e.getMessage());
            }
        }
        return savedeImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
    try{
           image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
           imageRepository.save(image);
        try {
                image.setImage(new SerialBlob(file.getBytes()));
        } catch (SQLException ex) {
                    throw new RuntimeException(ex.getMessage());
            }
    }catch(IOException e ){
              throw new RuntimeException(e.getMessage());
        }
    }
    
}
