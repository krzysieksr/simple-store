package com.example.sklep.user.converters;

import com.example.sklep.user.UserDto;
import com.example.sklep.user.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserDtoConverter implements Converter<UserEntity, UserDto> {
    @Override
    public UserDto convert(UserEntity source) {
        return UserDto.builder()
                .userId(source.getUserId())
                .username(source.getUsername())
                .fullName(source.getFullName())
                .build();
    }
}
