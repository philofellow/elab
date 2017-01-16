package com.zyans.elab.controller;

import com.zyans.elab.controller.LoginController;
import com.zyans.elab.domain.entity.User;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class UserHomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final CheckForAuthenticationService checkForAuthenticationService;
    private final UserService userService;

    @Value("${view.userhome}")
    private String viewUserHome;

    @Value("${rest.userhome}")
    private String restUserHome;

    @Value("${view.signupAndEdit}")
    private String viewUserEdit;

    @Value("${rest.useredit}")
    private String restUserEdit;

    @Value("${view.error}")
    private String viewError;

    @Autowired
    public UserHomeController(CheckForAuthenticationService checkForAuthenticationService,
                              UserService userService) {
        this.checkForAuthenticationService = checkForAuthenticationService;
        this.userService = userService;
    }

    @RequestMapping(value = "${rest.useredit}", method = RequestMethod.POST)
    public String editInformation(HttpServletRequest request, @Valid @ModelAttribute("form") UserForm form, Model model) {
        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (UserHome)");
            return checkForAuthenticationService.accessError(model);
        }
        LOGGER.debug("Editing a user: \"{}\"", form);

        userService.update(request.getAttribute("email").toString(), form);
        return "redirect:" + restUserHome;
    }

    @RequestMapping(value = "${rest.useredit}", method = RequestMethod.GET)
    public String fetchUserEdit(HttpServletRequest request, Model model, @Valid @ModelAttribute("form") UserForm form) {
        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (UserHome)");
            return checkForAuthenticationService.accessError(model);
        }

        model.addAttribute("actionType", restUserEdit);

        return viewUserEdit;
    }

    @RequestMapping(value = "${rest.userhome}", method = RequestMethod.GET)
    public String fetchUserHome(HttpServletRequest request, Model model) {
        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (UserHome)");
            return checkForAuthenticationService.accessError(model);
        }

        LOGGER.debug("User={} is fetching their homepage.", request.getSession().getAttribute("email").toString());

        // fetch various values to place in the user view
        populateUserValuesInView(request, model);

        return viewUserHome;
    }

    /**
     * Fetch the profile information associated with this particular user.
     *
     * @param request
     * @param model
     */
    private void populateUserValuesInView(HttpServletRequest request, Model model) {
        String email = request.getSession().getAttribute("email").toString();
        LOGGER.debug("User={} is fetching their profile.", email);
        model.addAttribute("user", userService.getUserByEmail(email));
    }
}
