package com.rsl.registrar;

import com.rsl.registrar.domain.UserCreateForm;
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
public class CreateUserIntegrationTest {
    @LocalServerPort
    private int port;

    @Value("${backend.rest.usercreate}")
    private String restUserCreate;

    /**
     * Note: TestRestTemplate is fault tolerant, so errors are not propagated
     * like in the RestTemplate class used in production.  This means we catch
     * exceptions in production, but assert faults in test environment (as we do below).
     */
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void successfulRegistration() throws Exception {
        // Create User
        UserCreateForm userCreateForm = new UserCreateForm();
        userCreateForm.setName("Foobar");
        userCreateForm.setAddressline1("Foobar Street");
        userCreateForm.setAddressline2("Apt 2");
        userCreateForm.setAddressline3("Room 1");
        userCreateForm.setCity("Reston");
        userCreateForm.setStateprovince("VA");
        userCreateForm.setCountrycode("US");
        userCreateForm.setPostalcode("20190");
        userCreateForm.setEmail("foobar@foorbar.com");
        userCreateForm.setPassword("foobar");
        userCreateForm.setPhone("123");
        userCreateForm.setFax("123");
        userCreateForm.setOrganization("foobar inc");

        ResponseEntity<String> entity = null;

        String localServer = "http://localhost:" + port;
        entity = restTemplate.postForEntity(localServer + restUserCreate, userCreateForm, String.class);

        assert(entity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void failRegistration() throws Exception {
        // Create User
        UserCreateForm userCreateForm = new UserCreateForm();
        userCreateForm.setName("Foobar");
        userCreateForm.setAddressline1("Foobar Street");
        userCreateForm.setAddressline2("Apt 2");
        userCreateForm.setAddressline3("Room 1");
        userCreateForm.setCity("Reston");
        userCreateForm.setStateprovince("VA");
        userCreateForm.setCountrycode("US");
        userCreateForm.setPostalcode("20190");
        userCreateForm.setEmail("fail400@foorbar.com");
        userCreateForm.setPassword("foobar");
        userCreateForm.setPhone("123");
        userCreateForm.setFax("123");
        userCreateForm.setOrganization("foobar inc");

        ResponseEntity<String> entity = null;

        String localServer = "http://localhost:" + port;
        entity = restTemplate.postForEntity(localServer + restUserCreate, userCreateForm, String.class);

        assert(entity.getStatusCode() == HttpStatus.BAD_REQUEST);
    }
}
