package com.nathdev.e_commerce.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nathdev.e_commerce.DTO.ImageDto;
import com.nathdev.e_commerce.DTO.ProductDto;
import com.nathdev.e_commerce.exceptions.ProductNotFoundException;
import com.nathdev.e_commerce.model.Category;
import com.nathdev.e_commerce.model.Image;
import com.nathdev.e_commerce.model.Product;
import com.nathdev.e_commerce.repository.CategoryRepository;
import com.nathdev.e_commerce.repository.ImageRepository;
import com.nathdev.e_commerce.repository.ProductRepository;
import com.nathdev.e_commerce.request.AddProductRequest;
import com.nathdev.e_commerce.request.ProductUpdateRequest;
import org.modelmapper.ModelMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ProductService implements I_ProductService {
     
       private final ProductRepository productRepository;
       private final CategoryRepository categoryRepository;
       private final ImageRepository imageRepository;
       private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
             .orElseGet(() -> {
               Category  newCategory = new Category(null, request.getCategory().getName(), null);
                 return categoryRepository.save(newCategory);
             } );
             request.setCategory(category);
             return productRepository.save(createProduct(request, category));
    }
       private Product createProduct(AddProductRequest request , Category category ){

          return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
          );
       }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
               .orElseThrow(() -> new ProductNotFoundException("Product not found !"));
    }
    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
          .ifPresentOrElse(productRepository::delete , ()-> {
             throw new ProductNotFoundException("Product not found !");
            });
    } 
    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
            return productRepository.findById(productId)
                    .map(existingProduct -> updateExistingProduct(existingProduct, request))
                    .map(productRepository::save)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found !"));
        }
               private Product updateExistingProduct(Product existingProduct , ProductUpdateRequest request){
                 existingProduct.setName(request.getName());
                 existingProduct.setBrand(request.getBrand());
                 existingProduct.setPrice(request.getPrice());
                 existingProduct.setInventory(request.getInventory());
                 existingProduct.setDescription(request.getDescription());
    
                 Category category = categoryRepository.findByName(request.getCategory().getName());
                 existingProduct.setCategory(category);
                 return existingProduct;
               }
    @Override
    public List<Product> getAllProduct() {
         return productRepository.findAll();
        }
        @Override
        public List<Product> getProductByCategory(String category) {
           return productRepository.findByCategory_Name(category);
        }
        @Override
        public List<Product> getProductByBrand(String brand) {
           return productRepository.findByBrand(brand);
        }
    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategory_NameAndBrand(category, brand);
    }
    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }
    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand ,name);
    }
    @Override

    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

   @Override

    public List<ProductDto>getConvertedProducts(List<Product> products ){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto  = modelMapper.map(product , ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image ->  modelMapper
        .map(image, ImageDto.class))
        .toList();
        productDto.setImage(imageDtos);
        return productDto;
    }
    
}