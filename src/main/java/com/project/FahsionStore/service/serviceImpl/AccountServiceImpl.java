package com.project.FahsionStore.service.serviceImpl;

import com.project.FahsionStore.model.Account;
import com.project.FahsionStore.repository.AccountRepository;
import com.project.FahsionStore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }
}
