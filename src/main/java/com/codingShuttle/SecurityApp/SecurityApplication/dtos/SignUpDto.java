package com.codingShuttle.SecurityApp.SecurityApplication.dtos;

import com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;

}
