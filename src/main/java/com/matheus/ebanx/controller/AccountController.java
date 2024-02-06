package com.matheus.ebanx.controller;

import com.matheus.ebanx.model.EventRequest;
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
    public ResponseEntity<String> event(@RequestBody EventRequest request) {
        String type = request.getType();
        Integer accountId = request.getDestination();
        Integer amount = request.getAmount();
        Integer origin = request.getOrigin();

        if(type.equalsIgnoreCase("deposit")){
            return accountService.deposit(accountId, amount);
        }
        if(type.equalsIgnoreCase("withdraw")){
            return accountService.withdraw(origin, amount);
        }
        if(type.equalsIgnoreCase("transfer")){
            return accountService.transfer(origin, accountId, amount);
        }
        return ResponseEntity.badRequest().body("Invalid request type");
    }

    @PostMapping("/reset")
    public ResponseEntity<String>reset(){
        return accountService.reset();
    }


}
