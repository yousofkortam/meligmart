package com.ecommerce.authentication.services.user;

import com.ecommerce.authentication.dto.UserDto;
import com.ecommerce.authentication.enums.Role;
import com.ecommerce.authentication.mapper.UserMapper;
import com.ecommerce.authentication.model.User;
import com.ecommerce.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    @Override
    public UserDto getDto(Long id) {
        return userMapper.toDto(
                this.get(id)
        );
    }

    @Override
    public User get(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    @Override
    public UserDto getDto(String email) {
        return userMapper.toDto(
                get(email)
        );
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User create(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setRole(user.getRole() == null ? Role.CUSTOMER : user.getRole());
        return userRepository.save(user);
    }

    @Override
    public UserDto update(UserDto userDto, Long id) {
        User user = this.get(id);
        if (!userDto.getEmail().equals(user.getEmail())) {
            if (this.exists(userDto.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        user = userMapper.toEntity(userDto);
        return userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public void delete(Long id) {
        User user = this.get(id);
        userRepository.delete(user);
    }

    @Override
    public boolean exists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }

}
