package com.nrapendra.account.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String name;
    private String accountNumber;
    private String phoneNumber;
    private String industry;
    private String billingCity;
    private String billingCountry;
}
