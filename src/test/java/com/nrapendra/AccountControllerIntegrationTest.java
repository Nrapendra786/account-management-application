package com.nrapendra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.models.Account;
import com.nrapendra.account.services.AccountService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Please Note: This test will work only if user provide all necessary credentials required in application-test.yml
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.yml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountService accountService;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/salesforce/accounts/";
    }

    private static String ACCOUNT_ID = "";

    @Test
    @Order(1)
    public void testCreateAccount() throws Exception {
        String url = getRootUrl() + "create/";
        int randomNumber = new Random(100).nextInt(1, 100);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", "test_name" + randomNumber)
                .queryParam("accountNumber", "1324435")
                .queryParam("phoneNumber", "89776566")
                .queryParam("billingCity", "ZH")
                .queryParam("billingCountry", "CH")
                .queryParam("industry", "Transportation");

        ResponseEntity<String> postResponse = restTemplate.withBasicAuth(USERNAME,PASSWORD)
                .postForEntity(builder.toUriString(), account(), String.class);

        var map = new ObjectMapper().readValue(postResponse.getBody(), Map.class);
        ACCOUNT_ID = (String) map.get("id");
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    public void testFindAccountById() throws Exception {

        ResponseEntity<String> getResponse =
                restTemplate
                        .withBasicAuth(USERNAME,PASSWORD)
                        .getForEntity(URI.create(getRootUrl() + ACCOUNT_ID), String.class);
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(3)
    public void testUpdateAccount() throws Exception {
        String url = getRootUrl() + "/" + ACCOUNT_ID;

        int randomNumber = new Random(100).nextInt(1, 100);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", "test_new_name" + randomNumber)
                .queryParam("accountNumber", "91324435")
                .queryParam("phoneNumber", "79776566")
                .queryParam("billingCity", "ZH")
                .queryParam("billingCountry", "CH")
                .queryParam("industry", "Transportation");

        ResponseEntity<String> putResponse = restTemplate
                .withBasicAuth(USERNAME,PASSWORD)
                .exchange(builder.toUriString(),
                        HttpMethod.PUT,
                        HttpEntity.EMPTY,
                        String.class);

         assertEquals(putResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(4)
    public void testDeleteAccount() throws Exception {
        ResponseEntity<String> deleteResponse = restTemplate.withBasicAuth(USERNAME,PASSWORD)
                .exchange(URI.create(getRootUrl() + ACCOUNT_ID),
                        HttpMethod.DELETE,
                        HttpEntity.EMPTY,
                        String.class);
        assertEquals(deleteResponse.getStatusCode(), HttpStatus.ACCEPTED);
    }

    private Account account() {
        return Account.builder().name("test_name")
                .accountNumber("1324435")
                .phoneNumber("89776566")
                .billingCity("ZH")
                .billingCountry("CH")
                .industry("Transportation")
                .build();
    }
}

