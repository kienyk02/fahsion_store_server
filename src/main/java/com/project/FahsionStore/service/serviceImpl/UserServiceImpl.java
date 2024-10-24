package com.project.FahsionStore.service.serviceImpl;

import com.project.FahsionStore.model.User;
import com.project.FahsionStore.repository.UserRepository;
import com.project.FahsionStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User findByAccountEmail(String accountEmail) {
        return userRepository.findByAccountEmail(accountEmail);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveOrUpdate(User user) {
        userRepository.save(user);
    }
}
