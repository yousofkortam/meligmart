package com.ecommerce.product.services.property;

import com.ecommerce.product.models.Property;
import com.ecommerce.product.repositories.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Override
    @Transactional
    public List<Property> saveProperties(List<Property> properties) {
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            properties.set(i, findOrCreateProperty(property.getTitle(), property.getValue()));
        }
        return properties;
    }

    @Override
    public Property findOrCreateProperty(String title, String value) {
        Optional<Property> existingProperty = propertyRepository.findByTitleAndValue(title, value);
        return existingProperty.orElseGet(
                () -> propertyRepository.save(
                        Property.builder()
                        .title(title)
                        .value(value)
                        .build())
        );
    }
}
