package com.example.sklep.user.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class CreateUserRequest {

    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String rePassword;
}
