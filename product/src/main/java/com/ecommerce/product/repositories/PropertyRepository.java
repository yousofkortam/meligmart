package com.ecommerce.product.repositories;

import com.ecommerce.product.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByTitleAndValue(String title, String value);
}
