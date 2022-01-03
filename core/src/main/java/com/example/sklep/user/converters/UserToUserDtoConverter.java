package com.example.sklep.user.converters;

import com.example.sklep.user.User;
import com.example.sklep.user.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .userId(source.getId())
                .username(source.getUsername())
                .fullName(source.getFullName())
                .build();

    }
}
