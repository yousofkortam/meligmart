package com.ecommerce.product.services.product;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.mappers.ProductMapper;
import com.ecommerce.product.models.Category;
import com.ecommerce.product.models.Product;
import com.ecommerce.product.models.Property;
import com.ecommerce.product.repositories.ProductRepository;
import com.ecommerce.product.services.category.CategoryService;
import com.ecommerce.product.services.property.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final PropertyService propertyService;

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        return productMapper.toDtoList(products);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Product with id " + id + " not found")
        );
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productMapper.toDto(getProduct(id));
    }

    @Override
    @Transactional
    public ProductDto addProduct(ProductDto product) {
        Category category = categoryService.getCategoryByName(product.getCategory());
        Product newProduct = productMapper.toEntity(product, category);
        List<Property> properties = propertyService.saveProperties(newProduct.getProperties());
        newProduct.setProperties(properties);
        return productMapper.toDto(
                productRepository.save(newProduct)
        );
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto product) {
        Product existingProduct = getProduct(id);
        Category category = categoryService.getCategoryByName(product.getCategory());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setCategory(category);
        List<Property> properties = propertyService.saveProperties(product.getProperties());
        existingProduct.setProperties(properties);
        return productMapper.toDto(
                productRepository.save(existingProduct)
        );
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        product.setProperties(null);
        productRepository.save(product);
        productRepository.delete(product);
    }
}
