package com.ecommerce.product.services.product;


import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.models.Product;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategory(String category);
    Product getProduct(Long id);
    ProductDto getProductById(Long id);
    ProductDto addProduct(ProductDto product);
    ProductDto updateProduct(Long id, ProductDto product);
    void deleteProduct(Long id);
}
