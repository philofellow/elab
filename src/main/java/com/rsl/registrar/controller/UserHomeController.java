package com.rsl.registrar.controller;

import com.rsl.registrar.domain.Domain;
import com.rsl.registrar.domain.User;
import com.rsl.registrar.domain.UserContactForm;
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
public class UserHomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final CheckForAuthenticationService checkForAuthenticationService;
    private final UserService userService;

    @Value("${backend.location}")
    private String backendHost;

    @Value("${frontend.view.userhome}")
    private String viewUserHome;

    @Value("${frontend.rest.userhome}")
    private String restUserHome;

    @Value("${frontend.view.signupAndEdit}")
    private String viewUserEdit;

    @Value("${frontend.rest.useredit}")
    private String restUserEditFrontend;

    @Value("${frontend.view.error}")
    private String viewError;

    @Autowired
    public UserHomeController(CheckForAuthenticationService checkForAuthenticationService,
                              UserService userService) {
        this.checkForAuthenticationService = checkForAuthenticationService;
        this.userService = userService;
    }

    @RequestMapping(value = "${frontend.rest.useredit}", method = RequestMethod.POST)
    public String editInformation(HttpServletRequest request, @RequestBody String formValues, Model model) {
        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (UserHome)");
            return checkForAuthenticationService.accessError(model);
        }
        LOGGER.debug("Editing a user: \"{}\"", formValues);

        try {
            ResponseEntity<?> entity = userService.createOrEditUser(request, formValues);
            if (entity.getStatusCode() == HttpStatus.OK) {
                return "redirect:" + restUserHome;
            }
        } catch (HttpClientErrorException e) {
            String reasonForFail = e.getResponseBodyAsString();
            model.addAttribute("error", "Account Update Errors: " + ExtractErrorService.extractUserCreateEditError(reasonForFail));
            return viewError;
        }
        model.addAttribute("error", "Unknown error during profile edits.");
        return viewError;
    }

    @RequestMapping(value = "${frontend.rest.useredit}", method = RequestMethod.GET)
    public String fetchUserEdit(HttpServletRequest request, Model model, UserCreateForm userCreateForm) {
        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (UserHome)");
            return checkForAuthenticationService.accessError(model);
        }

        ResponseEntity<User> entity = userService.getUserWithApiKey(
                request.getSession().getAttribute("email").toString(),
                request.getSession().getAttribute("apikey").toString());

        userCreateForm.setFromUser(entity.getBody());

        model.addAttribute("userCreateForm", userCreateForm);
        model.addAttribute("actionType", restUserEditFrontend);

        return viewUserEdit;
    }

    @RequestMapping(value = "${frontend.rest.userhome}", method = RequestMethod.GET)
    public String fetchUserHome(HttpServletRequest request, Model model) {
        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (UserHome)");
            return checkForAuthenticationService.accessError(model);
        }

        LOGGER.debug("User={} is fetching their homepage.", request.getSession().getAttribute("email").toString());

        model.addAttribute("email", request.getSession().getAttribute("email").toString());
        model.addAttribute("apikey", request.getSession().getAttribute("apikey").toString());

        // fetch various values to place in the user view
        populateUserValuesInView(request, model);

        // fetch domains
        populateUserDomainsInView(request, model);

        // fetch contacts
        populateUserContactsInView(request, model);

        return viewUserHome;
    }

    /**
     * Fetch the contacts associated with this particular user.
     *
     * @param request
     * @param model
     */
    private void populateUserContactsInView(HttpServletRequest request, Model model) {
        LOGGER.debug("User={} is fetching their contacts.", request.getSession().getAttribute("email").toString());

        ResponseEntity<UserContactForm[]> contactEntity = userService.getContactsByEmail(
                request.getSession().getAttribute("email").toString(),
                request.getSession().getAttribute("apikey").toString());
        model.addAttribute("contacts", contactEntity.getBody());
    }

    /**
     * Fetch the domains associated with this particular user.
     *
     * @param request
     * @param model
     */
    private void populateUserDomainsInView(HttpServletRequest request, Model model) {
        LOGGER.debug("User={} is fetching their domains.", request.getSession().getAttribute("email").toString());

        ResponseEntity<Domain[]> domainEntity = userService.getDomainsByEmail(
                request.getSession().getAttribute("email").toString(),
                request.getSession().getAttribute("apikey").toString());
        model.addAttribute("domains", domainEntity.getBody());
    }

    /**
     * Fetch the profile information associated with this particular user.
     *
     * @param request
     * @param model
     */
    private void populateUserValuesInView(HttpServletRequest request, Model model) {
        LOGGER.debug("User={} is fetching their profile.", request.getSession().getAttribute("email").toString());
        // fetch rest of user object
        ResponseEntity<User> entity = userService.getUserWithApiKey(
                request.getSession().getAttribute("email").toString(),
                request.getSession().getAttribute("apikey").toString());
        model.addAttribute("user", entity.getBody());
    }
}
