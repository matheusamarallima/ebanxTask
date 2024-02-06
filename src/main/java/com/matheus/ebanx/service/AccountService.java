package com.matheus.ebanx.service;

import com.matheus.ebanx.model.Account;
import org.springframework.http.HttpHeaders;
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
        initialAccount.setId(1);
        initialAccount.setBalance(100);
        accounts.add(initialAccount);
    }

    public ResponseEntity<Integer> checkBalance(Integer accountId) {
        Optional<Account> account = accounts.stream()
                .filter(accountStream ->
                        accountStream.getId().equals(accountId))
                .findFirst();

        if (account.isPresent()) {
            Account account1 = account.get();
            return ResponseEntity.ok(account1.getBalance());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
    }

    public ResponseEntity<String> deposit(Integer accountId, Integer amount) {
        Optional<Account> existAccount = accounts.stream().filter(account -> account.getId().equals(accountId)).findFirst();
        if (existAccount.isPresent()) {
            Account account = existAccount.get();
            account.setBalance(account.getBalance() + amount);
            accounts.set(accounts.indexOf(account), account);
            String response = String.format("{\"destination\":{\"id\":\"%d\",\"balance\":%d}", accountId, account.getBalance());
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
        }
        Account newAccount = new Account();
        newAccount.setId(accountId);
        newAccount.setBalance(amount);
        accounts.add(newAccount);
        String response = String.format("{\"destination\":{\"id\":\"%d\",\"balance\":%d}", accountId, newAccount.getBalance());
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }


    public ResponseEntity<String> withdraw(Integer accountId, Integer amount) {
        Optional<Account> existAccountWithdraw = accounts.stream().filter(account -> account.getId().equals(accountId)).findFirst();
        if (existAccountWithdraw.isPresent()) {
            Account account = existAccountWithdraw.get();
            account.setBalance(account.getBalance() - amount);
            accounts.set(accounts.indexOf(account), account);
            String response = String.format("{\"origin\":{\"id\":\"%d\",\"balance\":%d}", accountId, account.getBalance());
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.valueOf(0));
    }

    public ResponseEntity<String> transfer(Integer origin, Integer accountId, Integer amount) {
        Optional<Account> originAccount = accounts.stream().filter(account -> account.getId().equals(origin)).findFirst();
        Optional<Account> targetAccount = accounts.stream().filter(account -> account.getId().equals(accountId)).findFirst();
        if(targetAccount.isPresent() && originAccount.isPresent()){
            Account originAccount1 = originAccount.get();
            Account targetAccount1 = targetAccount.get();
            originAccount1.setBalance(originAccount1.getBalance() - amount);
            targetAccount1.setBalance(targetAccount1.getBalance() + amount);
            String response = String.format("{\"origin\":{\"id\":\"%d\",\"balance\":%d},\"destination\":{\"id\":\"%d\",\"balance\":%d}}",
                    originAccount1.getId(), originAccount1.getBalance(), targetAccount1.getId(), targetAccount1.getBalance());
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.valueOf(0));

    }

    public ResponseEntity<String> reset() {
        accounts.clear();
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}



