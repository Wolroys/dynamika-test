package com.dynamika.service;

import com.dynamika.dto.UserDto;
import com.dynamika.entity.User;
import com.dynamika.mapper.UserMapper;
import com.dynamika.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Async
    public CompletableFuture<List<UserDto>> findAll(){
        return CompletableFuture.completedFuture(
                userRepository.findAll()
                        .stream()
                        .map(user -> userMapper.map(user, UserDto.class))
                        .collect(Collectors.toList())
        );
    }

    @Async
    @Transactional
    public CompletableFuture<UserDto> create(UserDto userDto){
        return CompletableFuture.completedFuture(
                userMapper.map(
                        userRepository.save(userMapper.map(userDto, User.class)),
                        UserDto.class
                )
        );
    }

    @Async
    public CompletableFuture<Optional<UserDto>> findById(Long id){
        return CompletableFuture.completedFuture(
                userRepository.findById(id)
                        .map(user -> userMapper.map(user, UserDto.class))
        );
    }

    @Async
    @Transactional
    public CompletableFuture<Optional<UserDto>> update(Long id, UserDto updatedUser){
        return CompletableFuture.completedFuture(
                userRepository.findById(id)
                        .map(user -> userMapper.update(updatedUser, user))
                        .map(userRepository::save)
                        .map(user -> userMapper.map(user, UserDto.class))
        );
    }

    @Async
    @Transactional
    public CompletableFuture<Boolean> deleteUser(Long id){
        return CompletableFuture.completedFuture(
                userRepository.findById(id)
                        .map(user -> {
                            userRepository.delete(user);
                            return true;
                        }).orElse(false)
        );
    }
}

