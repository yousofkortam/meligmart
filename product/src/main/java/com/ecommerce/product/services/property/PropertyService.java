package com.ecommerce.product.services.property;


import com.ecommerce.product.models.Property;

import java.util.List;

public interface PropertyService {
    List<Property> saveProperties(List<Property> properties);
    Property findOrCreateProperty(String title, String value);
}
