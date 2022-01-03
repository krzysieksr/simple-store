package com.example.sklep.user;

import lombok.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Builder
public class UserDto {

    private int userId;
    private String username;
    private String fullName;
}
