package com.rsl.registrar.controller;

import com.rsl.registrar.domain.User;
import com.rsl.registrar.domain.UserLoginForm;
import com.rsl.registrar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Value("${frontend.admin.apikey}")
    private String adminApiKey;

    @Value("${frontend.rest.userhome}")
    private String restUserHome;

    @Value("${frontend.rest.index}")
    private String restIndex;

    @Value("${frontend.view.error}")
    private String viewError;

    @Value("${frontend.view.login}")
    private String viewLogin;

    @RequestMapping(value = "${frontend.rest.login}", method = RequestMethod.GET)
    public String fetchLoginView(UserLoginForm userLoginForm) {
        LOGGER.debug("Fetching userLoginForm.");
        return viewLogin;
    }

    @RequestMapping(value = "${frontend.rest.login}", method = RequestMethod.POST)
    public String checkLoginInformation(@Valid UserLoginForm userLoginForm, BindingResult bindingResult, ModelMap m, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return viewLogin;
        }

        LOGGER.debug("Processing form \"{}\" login request.", userLoginForm);

        try {
            ResponseEntity<User> entity = userService.getUserWithApiKey(userLoginForm.getEmail(), adminApiKey);

            if (!entity.getBody().getPasswordHash().equals(userLoginForm.getPassword())) {
                m.addAttribute("error", "Login failed, password is incorrect.");
                return viewError;
            }

            // set the user's apikey, id and email -- used for future REST calls
            request.getSession().setAttribute("apikey", entity.getBody().getApikey());
            request.getSession().setAttribute("registrantid", entity.getBody().getId());
            request.getSession().setAttribute("email", userLoginForm.getEmail());

            if (entity.getStatusCode() == HttpStatus.OK) {
                return "redirect:" + restUserHome;
            }
        } catch (HttpClientErrorException e) {
            m.addAttribute("error", "Login failed, could not find a username that matched.");
            return viewError;
        }
        m.addAttribute("error", "Unknown error occurred during login, please try again.");
        return viewError;
    }

    @RequestMapping(value = "${frontend.rest.logout}", method = RequestMethod.GET)
    public String logoutUser(ModelMap m, HttpServletRequest request) {
        LOGGER.debug("Processing logout request.");
        request.getSession().invalidate();
        return "redirect:" + restIndex;
    }
}
