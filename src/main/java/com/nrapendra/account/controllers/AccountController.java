package com.nrapendra.account.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.models.Account;
import com.nrapendra.account.services.AccountLocalDBService;
import com.nrapendra.account.services.AccountSalesforceService;
import com.nrapendra.applicationdata.ApplicationData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * TODO validation of input Data
 */

@RestController
@RequestMapping(value = "/api/salesforce/accounts")
@RequiredArgsConstructor
public class AccountController extends OpenAPIController {

    private final AccountSalesforceService accountService;

    private final AccountLocalDBService accountLocalDBService;

    @PostMapping("/create/")
    public ResponseEntity<?> createAccount(@RequestParam("name") String name,
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

        var response = accountService.createAccount(account);
        var httpStatus = HttpStatus.CREATED;

        long positiveUniqueUUID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        var applicationData = ApplicationData.builder().id(positiveUniqueUUID).request(account.toString()).response(mapResponseToMap(response)).httpStatusCode(httpStatus.value()).build();
        accountLocalDBService.saveApplicationDataToRepository(applicationData);

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable String id) throws IOException {

        var response = accountService.findAccountById(id);
        var httpStatus = HttpStatus.OK;

        long positiveUniqueUUID = Math.abs(UUID.randomUUID().getLeastSignificantBits());

        var applicationData = ApplicationData.builder().id(positiveUniqueUUID).request(id).response(mapResponseToMap(response)).httpStatusCode(httpStatus.value()).build();
        accountLocalDBService.saveApplicationDataToRepository(applicationData);

        return new ResponseEntity<>(response, HttpStatus.OK);
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

        var response = accountService.updateAccount(id, account);
        var httpStatus = HttpStatus.OK;
        long positiveUniqueUUID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        var applicationData = ApplicationData.builder().id(positiveUniqueUUID).request(account.toString()).response(mapResponseToMap(response)).httpStatusCode(httpStatus.value()).build();
        accountLocalDBService.saveApplicationDataToRepository(applicationData);

        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id) throws IOException {

        var response = accountService.deleteAccount(id);
        var httpStatus = HttpStatus.ACCEPTED;

        long positiveUniqueUUID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        var applicationData = ApplicationData.builder().id(positiveUniqueUUID).request(id).response(mapResponseToMap(response)).httpStatusCode(httpStatus.value()).build();
        accountLocalDBService.saveApplicationDataToRepository(applicationData);

        return new ResponseEntity<>(response, httpStatus);
    }

    private Map mapResponseToMap(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Map.class);
    }

}
