package com.nrapendra.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value="/api/salesforce/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestParam String name) throws IOException {
        return ResponseEntity.ok(accountService.createAccount(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAccount(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable String id, @RequestParam String name) throws IOException {
        return ResponseEntity.ok(accountService.updateAccount(id, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id) throws IOException {
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
