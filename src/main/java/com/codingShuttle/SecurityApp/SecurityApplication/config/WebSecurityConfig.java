package com.codingShuttle.SecurityApp.SecurityApplication.config;

import com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Role;
import com.codingShuttle.SecurityApp.SecurityApplication.filters.JwtFilter;
import com.codingShuttle.SecurityApp.SecurityApplication.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Permission.*;
import static com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Role.ADMIN;
import static com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtFilter jwtFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private static final String[] publicRoutes={ "/auth/**", "/signup", "/home.html","/login",
            "/oauth2/**"};

    @Bean
    SecurityFilterChain securityFilterChain(@NonNull HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET,"/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(ADMIN.name(),CREATOR.name())
                        .requestMatchers(HttpMethod.POST,"/posts/**")
                            .hasAnyAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.GET,"/posts/**")
                            .hasAnyAuthority(POST_VIEW.name())
                        .requestMatchers(HttpMethod.PUT,"/posts/**").hasAuthority(USER_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAuthority(POST_DELETE.name())




                        .anyRequest().authenticated())
                .csrf(csrfconfig -> csrfconfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(OAuth2Configurer -> OAuth2Configurer
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler));
        // .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }
//    @Bean
//    UserDetailsService myInMemoryUserDetailsService(){
//        UserDetails normaluserDetails= User
//                .withUsername("anuj")
//                .password(passwordEncoder().encode("anuj123"))
//                .roles("USER")
//                .build();
//        UserDetails adminUser=User
//                .withUsername("anuj_admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(normaluserDetails,adminUser);
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

