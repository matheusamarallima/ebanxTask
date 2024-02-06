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

    @PostMapping("/event")
    public ResponseEntity<String> event(@RequestParam(value = "origin", required = false) Integer origin, @RequestParam("type") String type, @RequestParam("destination") Integer accountId, @RequestParam("amount") Integer amount){
        if(type.equalsIgnoreCase("deposit")){
            return accountService.deposit(accountId, amount);
        }
        if(type.equalsIgnoreCase("withdraw")){
            return accountService.withdraw(accountId, amount);
        }
        if(type.equalsIgnoreCase("transfer")){
            return accountService.transfer(origin, accountId, amount);
        }
        return null;
    }


}
