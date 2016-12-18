package com.zyans.elab.controller;

import com.zyans.elab.domain.entity.User;
import com.zyans.elab.domain.form.LoginForm;
import com.zyans.elab.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @Value("${rest.userhome}")
    private String restUserHome;

    @Value("${rest.index}")
    private String restIndex;

    @Value("${view.error}")
    private String viewError;

    @Value("${view.login}")
    private String viewLogin;

    @RequestMapping(value = "${rest.login}", method = RequestMethod.GET)
    public String fetchLoginView(LoginForm loginForm) {
        LOGGER.debug("Fetching loginForm.");
        return viewLogin;
    }

    @RequestMapping(value = "${rest.login}", method = RequestMethod.POST)
    public String checkLoginInformation(@Valid LoginForm loginForm, BindingResult bindingResult, ModelMap m, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return viewLogin;
        }

        LOGGER.debug("Processing form \"{}\" login request.", loginForm);

        User user = userService.getUserByUsername(loginForm.getUsername()).get();
        if (!user.getPassword().equals(loginForm.getPassword())) {
                m.addAttribute("error", "Login failed, password is incorrect.");
                return viewError;
        }

        request.getSession().setAttribute("username", user.getUsername());
        request.getSession().setAttribute("role", user.getRole());
        return "redirect:" + restUserHome;
    }

    @RequestMapping(value = "${rest.logout}", method = RequestMethod.GET)
    public String logoutUser(ModelMap m, HttpServletRequest request) {
        LOGGER.debug("Processing logout request.");
        request.getSession().invalidate();
        return "redirect:" + restIndex;
    }
}
