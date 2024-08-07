package com.ecommerce.authentication.mapper;


import com.ecommerce.authentication.dto.UserDto;
import com.ecommerce.authentication.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
