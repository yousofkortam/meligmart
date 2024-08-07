package com.ecommerce.authentication.services.user;


import com.ecommerce.authentication.dto.UserDto;
import com.ecommerce.authentication.model.User;

public interface UserService {
    User get(Long id);
    UserDto getDto(Long id);
    User get(String email);
    UserDto getDto(String email);
    void save(User user);
    User create(UserDto userDto);
    UserDto update(UserDto userDto, Long id);
    void delete(Long id);
    boolean exists(String email);
    boolean exists(Long id);
}
