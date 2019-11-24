package com.petstore.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.dto.Pet;
import com.petstore.exception.PetStoreApiException;
import com.petstore.exception.PetStoreManagementException;
import com.petstore.service.PetService;
import com.petstore.util.ErrorGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of {@link PetService}.
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@Service
public class PetServiceImpl implements PetService {

    @Value("${petstore-api.pet.findByStatus}")
    private String getPetsByStatusApiUri;

    @Value("classpath:response.json")
    private Resource mockResponseFile;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public PetServiceImpl(@Autowired RestTemplate restTemplate, @Autowired ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Pet> getPetsByStatus(String status, boolean mockReponse) {
        if (mockReponse) {
            try {
                Pet[] pets = objectMapper.readValue(mockResponseFile.getFile(), Pet[].class);
                return List.of(pets);
            } catch (IOException ioex) {
                throw new PetStoreManagementException(ErrorGenerator.generateForCode("1006"), ioex);
            }
        } else {
            return getPetsByStatus(status);
        }
    }

    @Override
    public List<Pet> getPetsByStatus(String status) {
        log.debug("calling getPetsByStatus API for status {}", status);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getPetsByStatusApiUri)
                .queryParam("status", status);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<Pet>> response =
                restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Pet>>() {
                    }
                );
            log.info("API getPetsByStatus called. API status {}", response.getStatusCodeValue());
            return response.getBody();
        } catch (RestClientException rex) {
            throw new PetStoreApiException("getPetsByStatus", rex);
        }
    }
}
