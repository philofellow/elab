package com.rsl.registrar;

import com.rsl.registrar.domain.User;
import com.rsl.registrar.domain.UserLoginForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginUserIntegrationTest {
    @LocalServerPort
    private int port;

    @Value("${backend.rest.userlogin}")
    private String restUserLogin;

    /**
     * Note: TestRestTemplate is fault tolerant, so errors are not propagated
     * like in the RestTemplate class used in production.  This means we catch
     * exceptions in production, but assert faults in test environment (as we do below).
     */
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void successfulLogin() throws Exception {
        // Create User
        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setEmail("foobar@foobar.com");
        userLoginForm.setPassword("foobar");

        ResponseEntity<User> entity = null;

        String localServer = "http://localhost:" + port + restUserLogin.replace("{email}", userLoginForm.getEmail());
        entity = restTemplate.getForEntity(localServer +"?password=" + userLoginForm.getPassword(), User.class);

        assert(entity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void failLoginForbidden() throws Exception {
        // Create User
        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setEmail("fail403@foobar.com");
        userLoginForm.setPassword("foobar");

        ResponseEntity<User> entity = null;

        String localServer = "http://localhost:" + port + restUserLogin.replace("{email}", userLoginForm.getEmail());
        entity = restTemplate.getForEntity(localServer +"?password=" + userLoginForm.getPassword(), User.class);

        assert(entity.getStatusCode() == HttpStatus.FORBIDDEN);
    }

    @Test
    public void failLoginNotFound() throws Exception {
        // Create User
        UserLoginForm userLoginForm = new UserLoginForm();
        userLoginForm.setEmail("fail404@foobar.com");
        userLoginForm.setPassword("foobar");

        ResponseEntity<User> entity = null;

        String localServer = "http://localhost:" + port + restUserLogin.replace("{email}", userLoginForm.getEmail());
        entity = restTemplate.getForEntity(localServer +"?password=" + userLoginForm.getPassword(), User.class);

        assert(entity.getStatusCode() == HttpStatus.NOT_FOUND);
    }
}
