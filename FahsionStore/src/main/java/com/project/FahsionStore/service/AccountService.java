package com.project.FahsionStore.service;


import com.project.FahsionStore.model.Account;

public interface AccountService {
    Account findByEmail(String email);

    boolean existsByEmail(String email);
}
