package com.rsl.registrar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAccessIntegrationTest {
    // REST endpoints
    @Value("${frontend.rest.userhome}")
    private String restUserHome;

    @Value("${backend.rest.userlogin}")
    private String restUserLogin;

    // Values required to assert if correct behavior observed
    @Value("${error.accessError}")
    private String errorAccess;

    @Value("${stub.apikey}")
    private String stubAPIKey;


    @Autowired
    private MockMvc mockMvc;

    /**
     * These two functions should ensure that users can access pages when logged-in and cannot access pages when not
     * logged-in, e.g. get an access denied style error.
     */

    /**
     *
     * @throws Exception
     */
    @Test
    public void accessAllowed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(
                restUserHome).sessionAttr("apikey", stubAPIKey).sessionAttr("email", "demo@verisign.com")).
                andExpect(content().string(containsString(stubAPIKey)));
    }

    @Test
    public void accessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(restUserHome)).andExpect(content().string(containsString(errorAccess)));
    }
}
