package com.nrapendra.account.controllers;

import com.nrapendra.account.models.Account;
import com.nrapendra.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value="/api/salesforce/accounts")
@RequiredArgsConstructor
public class AccountController extends OpenAPIController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) throws IOException {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(accountService.findAccountById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable String id, @RequestParam String name) throws IOException {
        return ResponseEntity.ok(accountService.updateAccount(id, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
