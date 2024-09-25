package com.nrapendra.account.models;

import lombok.Data;

@Data
public class Account {
    private String name;
    private String accountNumber;
    private String phoneNumber;
    private String industry;
    private String billingCity;
    private int numberOfEmployee;
}
