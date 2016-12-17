package com.rsl.registrar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by akaizer on 11/15/16.
 */
@Service
public class CheckForAuthenticationService {
    @Value("${error.accessError}")
    private String errorAccess;

    @Value("${frontend.view.error}")
    private String viewError;

    /**
     * Function that validates if a user is logged-in based on their having access to an apikey in their session.
     * @param request Request holds access to the user's session, if it exists.
     * @return
     */
    public boolean isUserLoggedIn( HttpServletRequest request ) {
        if (request.getSession().getAttribute("apikey") == null) {
            return false;
        }
        return true;
    }

    /**
     * This function is called whenever an unauthenticated user tries to access an authenticated resource.
     * e.g. the user is not logged-in and they are trying to access pages only a logged-in user can access.
     *
     * @param model
     * @return
     */
    public String accessError(Model model) {
        model.addAttribute("error", errorAccess);
        return viewError;
    }
}
