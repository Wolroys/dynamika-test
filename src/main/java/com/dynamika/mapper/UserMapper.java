package com.dynamika.mapper;

import com.dynamika.dto.UserDto;
import com.dynamika.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public <T, R> R map(T source, Class<R> destinationClass){
        return source == null ? null : modelMapper.map(source, destinationClass);
    }

    public User update(UserDto source, User destination){
        modelMapper.map(source, destination);
        return destination;
    }
}
