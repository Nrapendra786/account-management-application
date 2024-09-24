package com.nrapendra.account.exceptions;

import lombok.Getter;

import java.util.Date;

@Getter
public record ErrorDetails(Date timestamp, String message, String details) {

}
