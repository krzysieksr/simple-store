package com.example.sklep.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@Setter
@AllArgsConstructor
public class User implements UserDetails {
    private int userId;
    private String username;
    private String password;
    private boolean enabled;
    private String fullName;

    public User(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.enabled = userEntity.isEnabled();
        this.fullName = userEntity.getFullName();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("User"));
    }

    public int getId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
