package com.dynamika.controller.rest;

import com.dynamika.dto.UserDto;
import com.dynamika.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerRest {

    private final UserService userService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<UserDto>>> getAllUsers(){
        return userService.findAll()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserDto>> getUserById(@PathVariable Long id){
        return userService.findById(id)
                .thenApply(userDto -> userDto.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<UserDto>> createUser(@RequestBody UserDto userDto){
        return userService.create(userDto)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserDto>> updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        return userService.update(id, userDto)
                .thenApply(user -> user.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id)
                .thenApply(aBoolean -> aBoolean ? ResponseEntity.ok().<Void>build() : ResponseEntity.notFound().<Void>build());
    }
}

