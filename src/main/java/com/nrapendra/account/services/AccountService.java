package com.nrapendra.account.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.exceptions.BadRequestException;
import com.nrapendra.account.exceptions.InternalServerError;
import com.nrapendra.account.salesforce.SalesforceConnector;
import com.nrapendra.account.salesforce.SalesforceObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.nrapendra.account.utils.AppUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final SalesforceAuthService authService;

    private final SalesforceConnector salesforceConnector;

    public String createAccount(String accountName) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();
        var post = salesforceConnector.createAccount(salesforceObject,accountName);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {
            checkResponseCode(response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity());
        }
    }

    public String findAccountById(String accountId) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();
        var get = salesforceConnector.findAccountById(salesforceObject,accountId);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(get)) {
            checkResponseCode(response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity());
        }
    }

    public String updateAccount(String accountId, String newName) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();
        var patch = salesforceConnector.updateAccount(salesforceObject,accountId,newName);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(patch)) {
             checkResponseCode(response.getStatusLine().getStatusCode());
             return responseMessage(accountId, response.getStatusLine().getStatusCode(), UPDATE_REQUEST + COLON + newName);
        }
    }

    public String deleteAccount(String accountId) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();
        var delete = salesforceConnector.deleteAccountById(salesforceObject,accountId);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(delete)) {
            checkResponseCode(response.getStatusLine().getStatusCode());
            return responseMessage(accountId, response.getStatusLine().getStatusCode(), DELETE_REQUEST);
        }
    }

    private void checkResponseCode(int responseCode){
        if(responseCode >= 400 && responseCode <=499){
            throw new BadRequestException();
        } else if (responseCode >= 500 && responseCode <=599){
            throw new InternalServerError();
        }
    }

    private String responseMessage(String accountId, int statusCode, String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put(ID, accountId);
        map.put(MESSAGE, message);
        map.put(STATUS_CODE, String.valueOf(statusCode));

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
      
      
      

    
    
    

