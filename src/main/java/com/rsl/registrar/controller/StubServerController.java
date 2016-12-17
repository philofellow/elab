package com.rsl.registrar.controller;

import com.rsl.registrar.domain.User;
import com.rsl.registrar.domain.UserCreateForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class returns stubbed responses to requests that would originally
 * be handled by the backend.
 *
 *
 * Created by akaizer on 11/4/16.
 */
@Controller
public class StubServerController {
    @Value("${stub.apikey}")
    private String stubAPIKey;

    @RequestMapping("${backend.rest.usercreate}")
    public ResponseEntity<String> createAccount(@RequestBody UserCreateForm userCreateForm) {
        if (userCreateForm.getEmail().contains("fail400")) {
            return new ResponseEntity<String>("Account already exists", HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("Stub server has received an account creation request");
            System.out.println(userCreateForm.toString());
            return new ResponseEntity<String>("", HttpStatus.OK);
        }
    }

    @RequestMapping("${backend.rest.userlogin}")
    public ResponseEntity<User> login(@RequestParam("password") String password, @PathVariable String email) {
        if (email.contains("fail403")) {
            return new ResponseEntity<User>(new User(), HttpStatus.FORBIDDEN);
        } else if (email.contains("fail404")) {
            return new ResponseEntity<User>(new User(), HttpStatus.NOT_FOUND);
        } else {
            System.out.println("Stub server has received an account login request");
            System.out.println(email + " " + password);
            User fakeUser = new User();
            fakeUser.setEmail("fake@foobar.com");
            fakeUser.setId((long) 1);
            fakeUser.setApikey(stubAPIKey);
            fakeUser.setPhone("123");
            fakeUser.setName("Foo Bar");
            fakeUser.setPasswordHash("***");
            return new ResponseEntity<User>(fakeUser, HttpStatus.OK);
        }
    }
}
