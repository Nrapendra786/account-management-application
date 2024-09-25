package com.nrapendra.account.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.config.AppConfig;
import com.nrapendra.account.salesforce.SalesforceObject;
import lombok.AllArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nrapendra.account.utils.AppUtil.*;

@Service
@AllArgsConstructor
public class SalesforceAuthService {

    private final AppConfig appConfig;

    public SalesforceObject getSalesforceObject() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(appConfig.tokenUrl());
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(GRANT_TYPE, "password"));
        params.add(new BasicNameValuePair(CLIENT_ID, appConfig.clientId()));
        params.add(new BasicNameValuePair(CLIENT_SECRET, appConfig.clientSecret()));
        params.add(new BasicNameValuePair(USERNAME, appConfig.username()));
        params.add(new BasicNameValuePair(PASSWORD, appConfig.password() + appConfig.securityToken()));

        post.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = client.execute(post);
        String responseBody = EntityUtils.toString(response.getEntity());

        JsonNode json = new ObjectMapper().readTree(responseBody);
        var salesforceObject = new SalesforceObject();
        salesforceObject.setAccessToken(json.get(ACCESS_TOKEN).asText());
        salesforceObject.setInstanceUrl(json.get(INSTANCE_URL).asText());
        return salesforceObject;
    }
}
