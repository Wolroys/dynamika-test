package com.dynamika.service;

import com.dynamika.dto.UserDto;
import com.dynamika.entity.User;
import com.dynamika.mapper.UserMapper;
import com.dynamika.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> findAll(){
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto create(UserDto userDto){
        return userMapper.map(
                userRepository.save(userMapper.map(userDto, User.class)),
                UserDto.class);
    }

    public Optional<UserDto> findById(Long id){
        return userRepository.findById(id)
                .map(user -> userMapper.map(user, UserDto.class));
    }

    @Transactional
    public Optional<UserDto> update(Long id, UserDto updatedUser){
        return userRepository.findById(id)
                .map(user -> userMapper.update(updatedUser, user))
                .map(userRepository::saveAndFlush)
                .map(user -> userMapper.map(user, UserDto.class));
    }

    public boolean deleteUser(Long id){
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                }).orElse(false);
    }
}
