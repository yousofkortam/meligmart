package com.ecommerce.product.mappers;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.models.Category;
import com.ecommerce.product.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", source = "category.name")
    ProductDto toDto(Product product);
    @Mapping(target = "category", source = "category")
    @Mapping(target = "name", source = "productDto.name")
    @Mapping(target = "id", source = "productDto.id")
    @Mapping(target = "properties", source = "productDto.properties")
    Product toEntity(ProductDto productDto, Category category);
    List<ProductDto> toDtoList(List<Product> products);
}
