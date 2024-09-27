package com.nrapendra.account.controllers;

import com.nrapendra.account.models.Account;
import com.nrapendra.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value="/api/salesforce/accounts")
@RequiredArgsConstructor

/**
 * TODO validation of input Data
 */

public class AccountController extends OpenAPIController {

    private final AccountService accountService;

    @PostMapping("/create/")
    public ResponseEntity<?> createAccount( @RequestParam("name") String name,
                                            @RequestParam("accountNumber") String accountNumber,
                                            @RequestParam("phoneNumber") String phoneNumber,
                                            @RequestParam("billingCity") String billingCity,
                                            @RequestParam("billingCountry") String billingCountry,
                                            @RequestParam("industry") String industry
                                           ) throws IOException {
        var account = Account.builder()
                .name(name)
                .accountNumber(accountNumber)
                .industry(phoneNumber)
                .phoneNumber(phoneNumber)
                .billingCity(billingCity)
                .billingCountry(billingCountry)
                .industry(industry).build();
        return new ResponseEntity<>(accountService.createAccount(account),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable String id) throws IOException {
        return new ResponseEntity<>(accountService.findAccountById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable String id,
                                           @RequestParam("name") String name,
                                           @RequestParam("accountNumber") String accountNumber,
                                           @RequestParam("phoneNumber") String phoneNumber,
                                           @RequestParam("billingCity") String billingCity,
                                           @RequestParam("billingCountry") String billingCountry,
                                           @RequestParam("industry") String industry) throws IOException {
        var account = Account.builder()
                .accountNumber(accountNumber)
                .billingCity(billingCity)
                .billingCountry(billingCountry)
                .phoneNumber(phoneNumber)
                .name(name)
                .industry(industry).build();
        return new ResponseEntity<>(accountService.updateAccount(id, account),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id) throws IOException {
        return new ResponseEntity<>(accountService.deleteAccount(id),HttpStatus.ACCEPTED);
    }
}
