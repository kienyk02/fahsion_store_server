package com.project.FahsionStore.service;


import com.project.FahsionStore.model.ERole;
import com.project.FahsionStore.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleName(ERole roleName);
}
