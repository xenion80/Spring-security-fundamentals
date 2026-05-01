package com.codingShuttle.SecurityApp.SecurityApplication.utils;

import com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Permission.*;
import static com.codingShuttle.SecurityApp.SecurityApplication.entities.enums.Role.*;

public class PermissionMapping {
   private static Map<Role, Set<Permission>> map =Map.of(
            USER,Set.of(USER_VIEW,POST_VIEW),
            CREATOR,Set.of(USER_VIEW,POST_VIEW,USER_UPDATE,POST_UPDATE),
            ADMIN,Set.of(USER_DELETE,USER_CREATE,POST_DELETE,USER_VIEW,POST_VIEW,USER_UPDATE,POST_UPDATE)
    );
   public static Set<SimpleGrantedAuthority> getAuthoritiesRole(Role role){
       return map.get(role).stream()
               .map(permission -> new SimpleGrantedAuthority(permission.name()))
               .collect(Collectors.toSet());
   }
}
