package com.matheus.ebanx.controller;

import com.matheus.ebanx.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/balance")
    public ResponseEntity<Integer> getBalance(@RequestParam("account_id") Integer accountId){
        return accountService.checkBalance(accountId);
    }


}
