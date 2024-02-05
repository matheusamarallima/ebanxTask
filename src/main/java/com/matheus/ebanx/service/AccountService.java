package com.matheus.ebanx.service;

import com.matheus.ebanx.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final List<Account> accounts = new ArrayList<>();

    public AccountService() {
        Account initialAccount = new Account();
        initialAccount.setAccountId(1);
        initialAccount.setBalance(100);
        accounts.add(initialAccount);
    }

    public ResponseEntity<Integer> checkBalance(Integer accountId) {
        Optional<Account> account = accounts.stream()
                .filter(accountStream ->
                        accountStream.getAccountId().equals(accountId))
                .findFirst();

        if(account.isPresent()){
            Account account1 = account.get();
            return ResponseEntity.ok(account1.getBalance());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
    }

}
