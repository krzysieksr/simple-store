package com.example.sklep.user.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class AuthRequest {
    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;
}
