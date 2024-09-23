package com.nrapendra.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Value("${salesforce.apiUrl}")
    private String apiUrl;

    private final SalesforceAuthService authService;

    public String createAccount(String accountName) throws IOException {

        String accessToken = authService.getSalesforceObject().getAccessToken();
        String salesforceInstanceUrl = authService.getSalesforceObject().getInstanceUrl();

        String url = salesforceInstanceUrl + "/services/data/v57.0/sobjects/Account/";
        HttpPost post = new HttpPost(url);
        post.setHeader("Authorization", "Bearer " + accessToken);
        post.setHeader("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> accountData = new HashMap<>();
        accountData.put("Name", accountName);

        String json = objectMapper.writeValueAsString(accountData);
        post.setEntity(new StringEntity(json));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {
            return EntityUtils.toString(response.getEntity());
        }
    }

    public String getAccountById(String accountId) throws IOException {
        String accessToken = authService.getSalesforceObject().getAccessToken();
        String salesforceInstanceUrl = authService.getSalesforceObject().getInstanceUrl();

        String url = salesforceInstanceUrl + "/services/data/v57.0/sobjects/Account/";

        HttpGet get = new HttpGet(url + "/" + accountId);
        get.setHeader("Authorization", "Bearer " +  accessToken);
        get.setHeader("Content-Type", "application/json");

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(get)) {
            return EntityUtils.toString(response.getEntity());
        }
    }

    // Update Account
    public String updateAccount(String accountId, String newName) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();

        HttpClient client = HttpClientBuilder.create().build();
        HttpPatch patch = new HttpPatch(apiUrl + "/" + accountId);
        patch.setHeader("Authorization", "Bearer " + salesforceObject.getAccessToken());
        patch.setHeader("Content-Type", "application/json");

        String json = "{ \"Name\": \"" + newName + "\" }";
        patch.setEntity(new StringEntity(json));

        HttpResponse response = client.execute(patch);
        return EntityUtils.toString(response.getEntity());
    }

    // Delete Account
    public String deleteAccount(String accountId) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();

        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete delete = new HttpDelete(apiUrl + "/" + accountId);
        delete.setHeader("Authorization", "Bearer " + salesforceObject.getAccessToken());

        HttpResponse response = client.execute(delete);
        return EntityUtils.toString(response.getEntity());
    }
}
      
      
      

    
    
    

