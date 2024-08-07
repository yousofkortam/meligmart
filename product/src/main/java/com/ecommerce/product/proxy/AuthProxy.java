package com.ecommerce.product.proxy;

import com.ecommerce.product.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AUTH-SERVICE", path = "auth")
public interface AuthProxy {
    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable Long id);

    @GetMapping("/users/exists/{id}")
    boolean exists(@PathVariable Long id);
}
