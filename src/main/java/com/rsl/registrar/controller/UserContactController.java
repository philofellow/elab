package com.rsl.registrar.controller;

import com.rsl.registrar.domain.*;
import com.rsl.registrar.service.BackendService;
import com.rsl.registrar.service.CheckForAuthenticationService;
import com.rsl.registrar.service.ExtractErrorService;
import com.rsl.registrar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserContactController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final CheckForAuthenticationService checkForAuthenticationService;

    private final UserService userService;

    @Value("${backend.location}")
    private String backendHost;

    @Value("${frontend.view.userhome}")
    private String viewUserHome;

    @Value("${frontend.view.addorupdatecontact}")
    private String viewAddOrUpdateContact;

    @Value("${frontend.rest.userhome}")
    private String restUserHome;

    @Value("${frontend.rest.contactadd}")
    private String restContactAdd;

    @Value("${frontend.rest.contactedit}")
    private String restContactEdit;

    @Value("${frontend.rest.contactdelete}")
    private String restContactDelete;

    @Value("${backend.rest.contacts}")
    private String backendContacts;

    @Value("${backend.rest.contact}")
    private String backendContact;

    @Value("${frontend.view.error}")
    private String viewError;

    @Autowired
    public UserContactController(CheckForAuthenticationService checkForAuthenticationService, UserService userService) {
        this.checkForAuthenticationService = checkForAuthenticationService;
        this.userService = userService;
    }

    @RequestMapping(value = "${frontend.rest.contactadd}", method = RequestMethod.GET)
    public String addContact(HttpServletRequest request, Model model, UserContactForm userContactForm) {
        LOGGER.debug("get add contact page");

        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (AddContact)");
            return checkForAuthenticationService.accessError(model);
        }

        model.addAttribute("actionType", restContactAdd);

        return viewAddOrUpdateContact;
    }

    @RequestMapping(value = "${frontend.rest.contactedit}/{contactid}", method = RequestMethod.GET)
    public String UpdateContact(@PathVariable("contactid") String contactid,
                                HttpServletRequest request,
                                Model model,
                                UserContactForm userContactForm)
            throws RestClientException{
        String email = request.getSession().getAttribute("email").toString();
        LOGGER.debug("get update contact page for user {}, contact {}", email, contactid);

        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (AddContact)");
            return checkForAuthenticationService.accessError(model);
        }

        // todo exception handling
        ResponseEntity<UserContactForm> contactEntity = userService.getContactByContactId(
                request.getSession().getAttribute("email").toString(),
                request.getSession().getAttribute("apikey").toString(),
                contactid);
        model.addAttribute("userContactForm", contactEntity.getBody());
        model.addAttribute("actionType", restContactEdit);

        return viewAddOrUpdateContact;
    }

    @RequestMapping(value = "${frontend.rest.contactadd}", method = RequestMethod.POST)
    public String addContact(@RequestBody String formValues, HttpServletRequest request, Model model) {
        LOGGER.debug("adding a contact: \"{}\"", formValues);

        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (AddContact)");
            return checkForAuthenticationService.accessError(model);
        }

        formValues += "&registrantid=" + request.getSession().getAttribute("registrantid");

        try {
            ResponseEntity<?> entity = BackendService.getInstance()
                    .PostToEndpoint(request, formValues, backendContact, true);

            if (entity.getStatusCode() == HttpStatus.OK) {
                return "redirect:" + restUserHome;
            }
        } catch (HttpClientErrorException e) {
           String reasonForFail = e.getResponseBodyAsString();
           //model.addAttribute("error", "Add Contact Errors: " + ExtractErrorService.extractUserCreateEditError(reasonForFail));
           model.addAttribute("error", "Add Contact Errors: " + reasonForFail);
           return viewError;
        }
        model.addAttribute("error", "Unknown error during adding contact.");
        return viewError;
    }

    @RequestMapping(value = "${frontend.rest.contactedit}", method = RequestMethod.POST)
    public String updateContact(@RequestBody String formValues, HttpServletRequest request, Model model) {
        LOGGER.debug("updating a contact: \"{}\"", formValues);

        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (UpdateContact)");
            return checkForAuthenticationService.accessError(model);
        }

        try {
            ResponseEntity<?> entity = BackendService.getInstance()
                    .PostToEndpoint(request, formValues, backendContact + "/" + request.getParameter("id"), true);

            if (entity.getStatusCode() == HttpStatus.OK) {
                return "redirect:" + restUserHome;
            }
        } catch (HttpClientErrorException e) {
           String reasonForFail = e.getResponseBodyAsString();
           //model.addAttribute("error", "Add Contact Errors: " + ExtractErrorService.extractUserCreateEditError(reasonForFail));
           model.addAttribute("error", "Add Contact Errors: " + reasonForFail);
           return viewError;
        }
        model.addAttribute("error", "Unknown error during adding contact.");
        return viewError;
    }

    @RequestMapping(value = "${frontend.rest.contactdelete}/{contactid}", method = RequestMethod.GET)
    public String deleteContact(@PathVariable("contactid") String contactid, HttpServletRequest request, Model model) {
        LOGGER.debug("deleting a contact: \"{}\"", contactid);

        if (!checkForAuthenticationService.isUserLoggedIn(request)) {
            LOGGER.debug("An unauthenticated user tried to access an authenticated resource (UpdateContact)");
            return checkForAuthenticationService.accessError(model);
        }

        try {
            userService.deleteContactByContactId(
                    request.getSession().getAttribute("email").toString(),
                    request.getSession().getAttribute("apikey").toString(),
                    contactid
                    );
            return "redirect:" + restUserHome;

        } catch (RestClientException e) {
           model.addAttribute("error", "Delete Contact Errors: " + e.getMessage());
           return viewError;
        }
    }
}
