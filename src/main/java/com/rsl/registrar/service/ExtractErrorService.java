package com.rsl.registrar.service;

/**
 * Created by akaizer on 11/17/16.
 */
public class ExtractErrorService {

    /**
     * Takes an error from the server and extracts the user friendly information.
     * @param errorCodes
     * @return
     */
    public static String extractUserCreateEditError(String errorCodes) {
        // todo not generally supported, redo or remove
        // the last 2 characters are always "} -- this is garbage that we want to remove from the user's view
        errorCodes = errorCodes.substring(0, errorCodes.length() - 2);
        String[] errorSplit = errorCodes.split("Error: ");

        String errorResponse = "<ul>";
        // skip "first" Error, which indicates that what follows is an error message
        for (int errors = 1; errors < errorSplit.length; errors++) {
            errorResponse += "<li>" + errorSplit[errors] + "</li>";
        }
        errorResponse += "</ul>";

        return errorResponse;
    }

}
