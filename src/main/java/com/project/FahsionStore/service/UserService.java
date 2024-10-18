package com.project.FahsionStore.service;

import com.project.FahsionStore.model.User;

import java.util.Optional;

public interface UserService {
    User findByAccountEmail(String accountEmail);

    Optional<User> getUserById(int id);

    void saveOrUpdate(User user);

}
