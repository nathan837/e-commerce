package com.nathdev.e_commerce.controller;

import java.util.List;

import org.apache.catalina.connector.Response;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
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

import com.nathdev.e_commerce.DTO.ProductDto;
import com.nathdev.e_commerce.exceptions.ResourceNotFoundException;
import com.nathdev.e_commerce.model.Product;
import com.nathdev.e_commerce.request.AddProductRequest;
import com.nathdev.e_commerce.request.ProductUpdateRequest;
import com.nathdev.e_commerce.response.ApiResponse;
import com.nathdev.e_commerce.service.product.I_ProductService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
         
        private final I_ProductService productService;
@GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProduct();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("SUCCESS", convertedProducts));
    }
@GetMapping("/product/{productId}/product")
     public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try{
        Product product = productService.getProductById(productId);
        ProductDto productDto = productService.convertToDto(product);
        return ResponseEntity.ok(new ApiResponse("SUCCESS", productDto));

        }catch(Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
     }
@PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try{
          Product theProduct = productService.addProduct(product);
          return ResponseEntity.ok(new ApiResponse("Add Product Success !", theProduct));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
@PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request , @PathVariable Long productId){
        try {
            Product product = productService.updateProduct(request, productId);
            return  ResponseEntity.ok(new ApiResponse("UPDATE SUCESS", product));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body( new ApiResponse(e.getMessage(), null));
        }
    }
@DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
             productService.deleteProductById(productId);
            return  ResponseEntity.ok(new ApiResponse("DELETE SUCESS", productId));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body( new ApiResponse(e.getMessage(), null));
        }
    }
@GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse>getProductByBrandAndName(@RequestParam String brandName , @RequestParam String productName){
        try{
            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
            
                if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NO PRODUCT FOUND ", productName));
                
             }
             List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("SUCCESS", convertedProducts));
        
    }catch(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
}
@GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse>getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try{
            List<Product> products = productService.getProductByCategoryAndBrand(category, brand);
             if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NO PRODUCT FOUND ",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("SUCCESS", products));
    
        }catch(Exception e){
             return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR!!", e.getMessage()));
    }
}
@GetMapping("/product{name}/products")
    public ResponseEntity<ApiResponse>getProductByName(@PathVariable String name){
        try {
            List<Product> products= productService.getProductByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found", null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", e.getMessage()));

        }
    }
@GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){ 
        try{
            List<Product>products = productService.getProductByBrand(brand);
        if (products.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NO PRODUCT FOUND", null));
        }
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
         return ResponseEntity.ok(new ApiResponse("SUCCESS", products));
    }catch(Exception e){
        return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
    }
}

@GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){ 
        try{
            List<Product>products = productService.getProductByCategory(category);
        if (products.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("NO PRODUCT FOUND", null));
        }
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
         return ResponseEntity.ok(new ApiResponse("SUCCESS", products));
    }catch(Exception e){
        return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
    }
}
     
@GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand , @RequestParam String name){
        try{
            var productcount = productService.countProductByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("product COUNT++", productcount));
        }catch(Exception e){
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}