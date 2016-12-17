package com.rsl.registrar.service;

import com.rsl.registrar.domain.Domain;
import com.rsl.registrar.domain.User;
import com.rsl.registrar.domain.UserContactForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by akaizer on 11/15/16.
 */
@Service
public class UserService {

    @Value("${backend.location}")
    private String backendHost;

    @Value("${backend.rest.userlogin}")
    private String restUserLogin;

    @Value("${backend.rest.useredit}")
    private String restUserEditBackend;

    @Value("${backend.rest.usercreate}")
    private String restUserCreate;

    @Value("${backend.rest.domains}")
    private String restUserDomains;

    @Value("${backend.rest.contacts}")
    private String restUserContacts;

    @Value("${backend.rest.contact}")
    private String restUserContact;

    public ResponseEntity<User> getUserWithApiKey(String email, String userApi) {
        RestTemplate r = new RestTemplate();

        String localServer = backendHost +
                restUserLogin.replace("{email}", email) +
                "?apikey=" + userApi;

        return r.getForEntity(localServer , User.class);
    }

    public ResponseEntity<Domain[]> getDomainsByEmail(String email, String userApi) {
        RestTemplate r = new RestTemplate();

        String localServer = backendHost +
                restUserDomains.replace("{email}", email) +
                "?apikey=" + userApi;

        return r.getForEntity(localServer, Domain[].class);
    }

    public ResponseEntity<UserContactForm[]> getContactsByEmail(String email, String userApi) {
        RestTemplate r = new RestTemplate();

        String localServer = backendHost +
                restUserContacts.replace("{email}", email) +
                "?apikey=" + userApi;

        return r.getForEntity(localServer, UserContactForm[].class);
    }

    public ResponseEntity<UserContactForm> getContactByContactId(String email, String userApi, String contactid) {
        RestTemplate r = new RestTemplate();

        String localServer = backendHost +
                restUserContact.replace("{email}", email) + "/" + contactid +
                "?apikey=" + userApi;

        return r.getForEntity(localServer, UserContactForm.class);
    }

    public void deleteContactByContactId(String email, String userApi, String contactid) {
        RestTemplate r = new RestTemplate();

        String localServer = backendHost +
                restUserContact.replace("{email}", email) + "/" + contactid +
                "?apikey=" + userApi;

        r.delete(localServer);
    }
    /**
     * Function that supports the create and edit user forms requests to the backend.
     *
     * @param request - is null for createUser, otherwise contains an apikey session when editing a user.
     * @param formValues - string that contains the POST body of the createUserForm class
     * @return an entity response that the calling function should handle
     */
    public ResponseEntity<String> createOrEditUser(HttpServletRequest request, String formValues) {

        ResponseEntity<String> entity = null;
        if (request == null) {
            entity = BackendService.getInstance().PostToEndpoint(request, formValues, restUserCreate, false);
        } else {
            entity = BackendService.getInstance().PutToEndpoint(request, formValues, restUserEditBackend, true);
        }
        return entity;
    }
}
