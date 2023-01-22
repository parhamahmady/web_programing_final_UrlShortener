package org.ce.wp.service.impl;

import org.ce.wp.service.RestClientService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Parham Ahmadi
 * @since 22.01.23
 */
@Service
public class RestClientServiceImpl implements RestClientService {
    private final RestTemplate restTemplate;

    public RestClientServiceImpl() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    @Override
    public int callApi(String uri) throws URISyntaxException {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URI(uri), String.class);
        return responseEntity.getStatusCode().value();
    }
}
