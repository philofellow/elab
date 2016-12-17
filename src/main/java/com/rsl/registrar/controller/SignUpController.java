package com.rsl.registrar.controller;

import com.rsl.registrar.domain.UserCreateForm;
import com.rsl.registrar.service.CheckForAuthenticationService;
import com.rsl.registrar.service.ExtractErrorService;
import com.rsl.registrar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;


@Controller
public class SignUpController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);
    private final CheckForAuthenticationService checkForAuthenticationService;
    private final UserService userService;

    @Value("${backend.location}")
    private String backendHost;

    @Value("${frontend.rest.signup}")
    private String restSignupUser;

    @Value("${frontend.rest.useredit}")
    private String restRedirectToEditUser;

    @Value("${frontend.view.accountCreationSuccess}")
    private String viewAccountCreationSuccess;

    @Value("${frontend.view.error}")
    private String viewError;

    @Value("${frontend.view.signupAndEdit}")
    private String viewSignupUser;

    @Autowired
    public SignUpController(CheckForAuthenticationService checkForAuthenticationService, UserService userService) {
        this.checkForAuthenticationService = checkForAuthenticationService;
        this.userService = userService;
    }

    @RequestMapping(value = "${frontend.rest.signup}", method = RequestMethod.GET)
    public String signup(HttpServletRequest request, UserCreateForm userCreateForm, Model model) {
        // if the user is already signed in, redirect them to edit their account rather than create a new one
        if (checkForAuthenticationService.isUserLoggedIn(request)) {
            return "redirect:" + restRedirectToEditUser;
        }

        model.addAttribute("actionType", restSignupUser);
        return viewSignupUser;
    }

    @RequestMapping(value = "${frontend.rest.signup}", method = RequestMethod.POST)
    public String checkSignupInformation(@RequestBody String formValues, Model m) {
        LOGGER.debug("Signing up a user={}", formValues);

        try {
            ResponseEntity<?> entity = userService.createOrEditUser(null, formValues);

            if (entity.getStatusCode() == HttpStatus.OK) {
                return viewAccountCreationSuccess;
            }
        } catch (HttpClientErrorException e) {
            String reasonForFail = e.getResponseBodyAsString();
            m.addAttribute("error", "Account Creation Errors: " + ExtractErrorService.extractUserCreateEditError(reasonForFail));
            return viewError;
        }
        m.addAttribute("error", "Unknown error during account creation.");
        return viewError;
    }
}
