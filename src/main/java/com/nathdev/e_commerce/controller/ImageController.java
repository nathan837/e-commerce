package com.nathdev.e_commerce.controller;

import org.springframework.http.HttpHeaders;
import java.sql.SQLException;
import java.util.List;

import javax.print.attribute.standard.Media;

import org.apache.coyote.Response;
import org.springframework.core.io.ByteArrayResource;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.nathdev.e_commerce.DTO.ImageDto;
import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.Image;
import com.nathdev.e_commerce.response.ApiResponse;
import com.nathdev.e_commerce.service.image.IimageService;
import com.nathdev.e_commerce.service.image.imageService;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    
    private final IimageService imageService;
   
@PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> file , @RequestParam Long ProductId){
        try{

        List<ImageDto> imageDtos = imageService.saveImages(file, ProductId);
        return ResponseEntity.ok(new ApiResponse("upload success !" , imageDtos));
        }catch( Exception e){

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("upload faild", e.getMessage()));
        
    }
}
/**
 * @param imageId
 * @return
 * @throws SQLException
 */
@GetMapping("/image/download/{imageId}")
    
public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) throws SQLException {
    Image image = imageService.getImageById(imageId);
    
    if (image == null || image.getImage() == null) {
        return ResponseEntity.status(NOT_FOUND)
                .body(null); // or return a proper ApiResponse with NOT_FOUND
    }

    byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
    ByteArrayResource resource = new ByteArrayResource(imageBytes);

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(image.getFileType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
            .body(resource);
}



@PutMapping("image/{imageId}/update")
  public ResponseEntity<ApiResponse> updateImage(@PathVariable Long ImageId , @RequestBody MultipartFile file){
    try{
     Image image = imageService.getImageById(ImageId);
     if (image != null) {
        imageService.updateImage(file, ImageId);
        return ResponseEntity.ok(new ApiResponse("Update success", null));
     }
    }catch(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body( new ApiResponse("Update faild", INTERNAL_SERVER_ERROR));
  }

  @DeleteMapping("image/{imageId}/delete")
  public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long ImageId){
    try{
     Image image = imageService.getImageById(ImageId);
     if (image != null) {
        imageService.deleteImageById(ImageId);
        return ResponseEntity.ok(new ApiResponse("delete success", null));
     }
    }catch(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
    return ResponseEntity.status(INTERNAL_SERVER_ERROR).body( new ApiResponse("Delete faild", INTERNAL_SERVER_ERROR));
  }

}
 