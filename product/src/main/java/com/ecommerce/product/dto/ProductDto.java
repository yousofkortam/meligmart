package com.ecommerce.product.dto;

import com.ecommerce.product.models.Property;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private Long id;
    @NotNull(message = "Product name is required")
    @NotBlank(message = "Product name is required")
    private String name;
    @NotNull(message = "Product price is required")
    private Float price;
    private int stock;
    private String category;
    @NotNull(message = "Product properties are required")
    private List<Property> properties;
}
