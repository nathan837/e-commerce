package com.nathdev.e_commerce.service.product;

import java.util.List;

import com.nathdev.e_commerce.DTO.ProductDto;
import com.nathdev.e_commerce.model.Product;
import com.nathdev.e_commerce.request.AddProductRequest;
import com.nathdev.e_commerce.request.ProductUpdateRequest;

public interface I_ProductService {

    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    ProductDto convertToDto(Product product);
    Product updateProduct(ProductUpdateRequest product, Long productId); // ✅ Correct Signature
    List<Product> getAllProduct();
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category , String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductsByBrandAndName(String category , String name);
    List<ProductDto> getConvertedProducts(List<Product> products);
    Long countProductByBrandAndName(String brand ,String name);
}

