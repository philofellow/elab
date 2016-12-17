/**
 * utilities to talk to backend
 */

package com.rsl.registrar.service;

import com.rsl.registrar.domain.Domain;
import com.rsl.registrar.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BackendService {

    @Value("${backend.location}")
    private String backendHost;

    private static AtomicReference<BackendService> INSTANCE = new AtomicReference<BackendService>();

    public BackendService() {
        final BackendService previous = INSTANCE.getAndSet(this);
        if(previous != null)
            throw new IllegalStateException("Second singleton " + this + " created after " + previous);
    }

    public static BackendService getInstance() {
        return INSTANCE.get();
    }

    private HttpEntity<?> PrepareHttpEntity(String formValues){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<?> httpEntity = new HttpEntity<>(formValues, requestHeaders);
        return httpEntity;
    }

    private RestTemplate PrepareRestTemplate() {
        RestTemplate r = new RestTemplate();
        StringHttpMessageConverter sCon = new StringHttpMessageConverter();
        sCon.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.TEXT_PLAIN));
        r.getMessageConverters().add(sCon);
        return r;
    }

    public ResponseEntity<String> PostToEndpoint(HttpServletRequest request, String formValues, String endpoint, boolean isUser) {
        RestTemplate r = PrepareRestTemplate();
        HttpEntity<?> httpEntity = PrepareHttpEntity(formValues);
        ResponseEntity<String> entity;
        if (!isUser) {
            entity = r.postForEntity(backendHost + endpoint, httpEntity, String.class);
        } else {
            entity = r.postForEntity(backendHost + endpoint.replace("{email}",
                    request.getSession().getAttribute("email").toString()) +
                            "?apikey=" + request.getSession().getAttribute("apikey"),
                    httpEntity,
                    String.class);
        }
        return entity;
    }
    public ResponseEntity<String> PutToEndpoint(HttpServletRequest request, String formValues, String endpoint, boolean isUser) {

        RestTemplate r = PrepareRestTemplate();
        HttpEntity<?> httpEntity = PrepareHttpEntity(formValues);
        ResponseEntity<String> entity;
        if (!isUser) {
               entity = r.exchange(backendHost + endpoint,
                    HttpMethod.PUT,
                    httpEntity,
                    String.class,
                    new HashMap<>());
        } else {
            entity = r.exchange(backendHost + endpoint.replace("{email}",
                    request.getSession().getAttribute("email").toString()) +
                            "?apikey=" + request.getSession().getAttribute("apikey"),
                    HttpMethod.PUT,
                    httpEntity,
                    String.class,
                    new HashMap<>());
        }
        return entity;
    }
}
