package com.petstore.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.petstore.constant.Status;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
public class PetWireMockJuint5Test {
    WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        setupStub();
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    public void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo("/api/v1/pet/byStatus?status=available&mockResponse=true"))
            .willReturn(aResponse().withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withStatus(200)
                .withBodyFile("json/response.json")));
    }

    @Test
    public void getPetsByStatus_shouldPass_withWireMockResponse() {
        String name = "doggie";
        ObjectMapper objectMapper = new ObjectMapper();
        Response response = given().when().get("http://localhost:8090/api/v1/pet/byStatus?status=available" +
            "&mockResponse=true");
        com.petstore.dto.Response apiResponse = response.as(com.petstore.dto.Response.class);
        List pets = (ArrayList) apiResponse.getData();
        long totalPetsWithNameDoggie = pets.stream().filter(o -> {
            LinkedHashMap pet = (LinkedHashMap) o;
            return "doggie".equals(pet.get("name"));
        }).count();
        assertEquals(Status.SUCCESS, apiResponse.getStatus());
        assertEquals(200, apiResponse.getCode().intValue());
        assertEquals(4195, pets.size());
        assertEquals(2611, totalPetsWithNameDoggie);
    }
}
