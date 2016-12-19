package com.zyans.elab.controller;

import com.zyans.elab.domain.form.UserForm;
import com.zyans.elab.service.CheckForAuthenticationService;
import com.zyans.elab.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class SignUpController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpController.class);
    private final CheckForAuthenticationService checkForAuthenticationService;
    private final UserService userService;

    @Value("${rest.signup}")
    private String restSignupUser;

    @Value("${rest.userhome}")
    private String restUserhome;

    @Value("${view.accountCreationSuccess}")
    private String viewAccountCreationSuccess;

    @Value("${view.error}")
    private String viewError;

    @Value("${view.signupAndEdit}")
    private String viewSignupUser;


    @Autowired
    public SignUpController(CheckForAuthenticationService checkForAuthenticationService, UserService userService) {
        this.checkForAuthenticationService = checkForAuthenticationService;
        this.userService = userService;
    }

    @RequestMapping(value = "${rest.signup}", method = RequestMethod.GET)
    public String signup(HttpServletRequest request, UserForm form, Model model) {
        // if the user is already signed in, redirect them to homepage
        if (checkForAuthenticationService.isUserLoggedIn(request)) {
            return "redirect:" + restUserhome;
        }

        model.addAttribute("actionType", restSignupUser);
        return viewSignupUser;
    }

    @RequestMapping(value = "${rest.signup}", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute("form") UserForm form, Model m, BindingResult result) {
        LOGGER.debug("Signing up a user: \"{}\"", form);
        // todo may be add validator here

        if(result.hasErrors()) {
            LOGGER.debug("signup form has error");
            m.addAttribute("error", "Account Creation Errors: " + result.getAllErrors());
            return viewError;
        }

        userService.create(form);
        return viewAccountCreationSuccess;
    }
}
