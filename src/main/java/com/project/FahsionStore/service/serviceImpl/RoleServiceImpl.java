package com.project.FahsionStore.service.serviceImpl;

import com.project.FahsionStore.model.ERole;
import com.project.FahsionStore.model.Role;
import com.project.FahsionStore.repository.RoleRepository;
import com.project.FahsionStore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<Role> findByRoleName(ERole roleName) {
        return roleRepository.findByRoleName(roleName);
    }

}
