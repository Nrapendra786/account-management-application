package com.nrapendra.account.salesforce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static com.nrapendra.account.utils.AppUtil.*;

@Component
public class SalesforceConnector {

    public HttpRequestBase createAccount(SalesforceObject salesforceObject, String accountName) throws JsonProcessingException, UnsupportedEncodingException {

        String accessToken = salesforceObject.getAccessToken();
        String salesforceInstanceUrl = salesforceObject.getInstanceUrl();

        String url = salesforceInstanceUrl + SALESFORCE_ACCOUNT_URL;
        HttpPost post = new HttpPost(url);
        post.setHeader(AUTHORIZATION, BEARER + accessToken);
        post.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        var accountData = new HashMap<>();
        accountData.put(NAME, accountName);
        String json = objectMapper.writeValueAsString(accountData);
        post.setEntity(new StringEntity(json));
        return post;
    }

    public HttpRequestBase findAccountById(SalesforceObject salesforceObject, String accountId){

        String accessToken = salesforceObject.getAccessToken();
        String salesforceInstanceUrl = salesforceObject.getInstanceUrl();

        String url = salesforceInstanceUrl + SALESFORCE_ACCOUNT_URL;

        HttpGet get = new HttpGet(url + "/" + accountId);
        get.setHeader(AUTHORIZATION, BEARER + accessToken);
        get.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return get;
    }

    public HttpRequestBase deleteAccountById(SalesforceObject salesforceObject, String accountId){
        String accessToken = salesforceObject.getAccessToken();
        String salesforceInstanceUrl = salesforceObject.getInstanceUrl();

        String url = salesforceInstanceUrl + SALESFORCE_ACCOUNT_URL;

        HttpDelete delete = new HttpDelete(url + "/" + accountId);
        delete.setHeader(AUTHORIZATION, BEARER + accessToken);
        delete.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return delete;
    }

    public HttpRequestBase updateAccount(SalesforceObject salesforceObject,String accountId, String newName) throws UnsupportedEncodingException, JsonProcessingException {
        String accessToken = salesforceObject.getAccessToken();
        String salesforceInstanceUrl = salesforceObject.getInstanceUrl();

        String url = salesforceInstanceUrl + SALESFORCE_ACCOUNT_URL;

        HttpPatch patch = new HttpPatch(url + "/" + accountId);
        patch.setHeader(AUTHORIZATION, BEARER + accessToken);
        patch.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        var accountData = new HashMap<>();
        accountData.put(NAME, newName);

        String json = objectMapper.writeValueAsString(accountData);
        patch.setEntity(new StringEntity(json));
        return patch;
    }
}

