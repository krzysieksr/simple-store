package com.example.sklep.security;

import com.example.sklep.user.User;
import com.example.sklep.user.UserEntity;
import com.example.sklep.user.UserRepository;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final Logger logger;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(UserRepository userRepository, Logger logger, JwtTokenFilter jwtTokenFilter) {
        super();
        this.userRepository = userRepository;
        this.logger = logger;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    // Set password encoding schema
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //Configure the authentication manager with the correct provider
    //Happens on authenticationManager.authenticate(
    //                new UsernamePasswordAuthenticationToken.....
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username))
                        );
                return new User(userEntity);
            }
        });
    }

    // Configure web security (public URLs, private URLs, authorization, etc.)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            logger.error("Unauthorized request - {}", ex.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                        }
                )
                .and();

        http.authorizeRequests()
                // Swagger endpoints must be publicly accessible
                .antMatchers("/").permitAll()
                // Our public endpoints
                .antMatchers("/public/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET, "/products/**").permitAll()
                .anyRequest().authenticated()
                //to get access to h2 console //TODO not on prod!
                .and().headers().frameOptions().disable();


        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // Expose authentication manager bean
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
