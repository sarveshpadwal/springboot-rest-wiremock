package com.petstore.web.controller;

import com.petstore.dto.Pet;
import com.petstore.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.MessageFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class PetControllerIT {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = null;
    private HttpHeaders headers = null;

    /**
     * Setup for before each test case
     */
    @BeforeEach
    public void setup() {
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void getPetsByStatus_shouldPass_whenGivenStatusAvailableAndNameDoggie() {
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String status = "available";
        String name = "doggie";
        long expectedPetsWithGivenName = 2612;
        ResponseEntity<Response<List<Pet>>> responseEntity = restTemplate.exchange(
            createURLWithPort("/api/v1/pet/byStatus?status=" + status),
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Response<List<Pet>>>() {
            }
        );
        Response<List<Pet>> response = responseEntity.getBody();
        assertNotNull(response);
        List<Pet> pets = response.getData();
        assertNotNull(pets);
        log.debug("total available pets found ={}", pets.size());
        long actualPetsWithGivenName = pets.stream().filter(pet -> name.equals(pet.getName())).count();
        log.debug("total pets with name {} ={}", name, actualPetsWithGivenName);

        assertEquals(expectedPetsWithGivenName, actualPetsWithGivenName,
            MessageFormat.format("Expected {0} pets with name {1} but found {2}",
                expectedPetsWithGivenName, name, actualPetsWithGivenName));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
