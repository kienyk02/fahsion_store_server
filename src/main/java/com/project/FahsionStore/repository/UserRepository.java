package com.project.FahsionStore.repository;

import com.project.FahsionStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccountEmail(String accountEmail);
}
