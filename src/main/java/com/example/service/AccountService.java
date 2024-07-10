package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {
    AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository){
        this.repository = repository;
    }

    public Account userRegistration(Account account){
        if(account.getUsername() != "" && account.getPassword().length() >= 4){
            Optional<Account> check = repository.findAccountByUsername(account.getUsername());
            if(check.isEmpty()){
                return repository.save(account);
            }
            return null;
        }
        return null;
    }

    public Account login(Account account){
        Optional<Account> check = repository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(check.isPresent()){
            return check.get();
        }
        return null;
    }

    public boolean verify(int accountId){
        Optional<Account> check = repository.findById(accountId);
        return check.isPresent();
    }
}
