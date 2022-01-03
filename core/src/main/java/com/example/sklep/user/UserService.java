package com.example.sklep.user;

import com.example.sklep.user.converters.UserEntityToUserDtoConverter;
import com.example.sklep.user.requests.CreateUserRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityToUserDtoConverter userEntityToUserDtoConverter;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, UserEntityToUserDtoConverter userEntityToUserDtoConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEntityToUserDtoConverter = userEntityToUserDtoConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
        return new User(userEntity);
    }

    @Transactional
    public UserDto create(CreateUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException(String.format("User having user name %s already exists", request.getUsername()));
        }
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Passwords don't match");
        }

//        User user = User.builder().username(request.getUsername())
//                .password(passwordEncoder.encode(
//                        request.getPassword())).build();

        UserEntity userEntity = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(
                        request.getPassword()))
                .enabled(true)
                .build();

        UserEntity saved = userRepository.save(userEntity);

        return userEntityToUserDtoConverter.convert(saved);
    }


    @Transactional
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
