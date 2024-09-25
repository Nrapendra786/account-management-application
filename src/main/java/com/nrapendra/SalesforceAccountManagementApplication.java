package com.nrapendra;

import com.nrapendra.account.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class SalesforceAccountManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesforceAccountManagementApplication.class, args);
	}

}
