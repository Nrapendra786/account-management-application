package com.nrapendra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.controllers.AccountController;
import com.nrapendra.account.models.Account;
import com.nrapendra.account.services.AccountLocalDBService;
import com.nrapendra.account.services.AccountSalesforceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.TransactionManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AccountControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountSalesforceService accountService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AccountLocalDBService accountLocalDBService;

  //  @MockBean
  //  private TransactionManager transactionManager;

    private static final String ACCOUNT_URL = "/api/salesforce/accounts";

    @Test
    public void testCreateAccount() throws Exception {

        //given
        var account = account();

        //when
        when(accountService.createAccount(any())).thenReturn(asJsonString(account));

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .post(ACCOUNT_URL + "/create/")
                        .param("name", "test")
                        .param("accountNumber", "1324435")
                        .param("phoneNumber", "89776566")
                        .param("billingCity", "ZH")
                        .param("billingCountry", "CH")
                        .param("industry", "Transportation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(account))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testUpdateAccount() throws Exception {

        //given
        var account = account();
        account.setBillingCity("Basel");
        account.setAccountNumber("QWER98765");

        //when
        when(accountService.updateAccount(any(), any())).thenReturn(asJsonString(account));

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .put(ACCOUNT_URL + "/{id}", 1)
                        .param("name", "test")
                        .param("accountNumber", "1324435")
                        .param("phoneNumber", "89776566")
                        .param("billingCity", "ZH")
                        .param("billingCountry", "CH")
                        .param("industry", "Transportation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.billingCity").value("Basel"))
                .andExpect(jsonPath("$.accountNumber").value("QWER98765"));
    }

    @Test
    public void testGetAccountById() throws Exception {

        //when
        when(accountService.findAccountById("1")).thenReturn(asJsonString(account()));

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ACCOUNT_URL + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("THL09876"))
                .andExpect(jsonPath("$.phoneNumber").value("+41999999999"))
                .andExpect(jsonPath("$.name").value("ABC International"))
                .andExpect(jsonPath("$.industry").value("Transportation"))
                .andExpect(jsonPath("$.billingCity").value("Zurich"))
                .andExpect(jsonPath("$.billingCountry").value("Switzerland"));
    }

    @Test
    public void testDeleteAccount() throws Exception {

        //when
        when(accountService.deleteAccount("1")).thenReturn(asJsonString(account()));

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(ACCOUNT_URL + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Account account() {
        return Account.builder()
                .name("ABC International")
                .accountNumber("THL09876")
                .billingCity("Zurich")
                .industry("Transportation")
                .phoneNumber("+41999999999")
                .billingCountry("Switzerland")
                .build();
    }
}
