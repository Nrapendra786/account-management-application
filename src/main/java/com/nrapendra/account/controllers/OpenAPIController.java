package com.nrapendra.account.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Tag(name = "AccountController", description = "AccountController enables users to perform several operations " +
        "including read, write, update and delete account")
public abstract class OpenAPIController {

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content( mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema(),mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema(),mediaType = "application/json") })
    })
    @Operation(summary = "CREATE ACCOUNT")
    public abstract ResponseEntity<?> createAccount(@RequestParam String name) throws IOException;

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content( mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema(),mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema(),mediaType = "application/json") })
    })
    @Operation(summary = "GET ACCOUNT")
    public abstract ResponseEntity<?> getAccount(@PathVariable String id) throws IOException;

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content( mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema(),mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema(),mediaType = "application/json") })
    })
    @Operation(summary = "UPDATE ACCOUNT")
    public abstract ResponseEntity<?> updateAccount(@PathVariable String id, @RequestParam String name) throws IOException;

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content( mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema(),mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema(),mediaType = "application/json") })
    })
    @Operation(summary = "DELETE ACCOUNT")
    public abstract ResponseEntity<?> deleteAccount(@PathVariable String id) throws IOException;
}
