package com.rsl.registrar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Value("${frontend.view.index}")
    private String viewIndex;

    @RequestMapping("${frontend.rest.index}")
    public String getIndex() {
        LOGGER.debug("Fetching index.");
        return viewIndex;
    }

}
