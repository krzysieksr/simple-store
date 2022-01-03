package com.example.sklep.user;

import com.example.sklep.security.JwtTokenUtil;
import com.example.sklep.user.converters.UserToUserDtoConverter;
import com.example.sklep.user.requests.AuthRequest;
import com.example.sklep.user.requests.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("public")
@AllArgsConstructor
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserToUserDtoConverter userConverter;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),
                        request.getPassword()));

        User user = (User) authenticate.getPrincipal();

        String token = jwtTokenUtil.generateAccessToken(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(userConverter.convert(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.create(request));
    }

    // Ony to test N+1 problem.
//    @GetMapping("/users")
//    public Collection<UserEntity> getAllUsers() {
//        return userService.getAllUsers();
//    }


}
