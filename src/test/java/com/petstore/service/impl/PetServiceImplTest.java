package com.petstore.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.config.ErrorMessageSourceConfig;
import com.petstore.dto.Category;
import com.petstore.dto.Pet;
import com.petstore.dto.Tag;
import com.petstore.exception.PetStoreApiException;
import com.petstore.service.PetService;
import com.petstore.util.ErrorGeneratorInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sarvesh
 * @version 0.0.1
 *
 * @since 0.0.1
 */
@ExtendWith(SpringExtension.class)
@Import({ErrorGeneratorInitializer.class, ErrorMessageSourceConfig.class})
class PetServiceImplTest {
    private static final String GET_PETS_BY_STATUS_API_URI_MOCK = "https://mockUrl.com";

    PetService petService;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * Setup for before each test case
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        petService = new PetServiceImpl(restTemplate, new ObjectMapper());

        ReflectionTestUtils.setField(petService, "getPetsByStatusApiUri", GET_PETS_BY_STATUS_API_URI_MOCK);
    }

    @Test
    void getPetsByStatus_shouldPass_forAvailableStatus() {
        /*********** Setup ************/
        String status = "available";
        Pet pet = preparePetForStatus(status);
        ResponseEntity<List<Pet>> myEntity = new ResponseEntity<List<Pet>>(List.of(pet), HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
            ArgumentMatchers.eq(GET_PETS_BY_STATUS_API_URI_MOCK + "?status=" + status),
            ArgumentMatchers.eq(HttpMethod.GET),
            ArgumentMatchers.<HttpEntity<List<Pet>>>any(),
            ArgumentMatchers.<ParameterizedTypeReference<List<Pet>>>any())
        ).thenReturn(myEntity);
        /*********** Execute ************/
        List<Pet> pets = petService.getPetsByStatus(status);
        /*********** Verify/Assertions ************/
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(
            ArgumentMatchers.eq(GET_PETS_BY_STATUS_API_URI_MOCK + "?status=" + status),
            ArgumentMatchers.eq(HttpMethod.GET),
            ArgumentMatchers.<HttpEntity<List<Pet>>>any(),
            ArgumentMatchers.<ParameterizedTypeReference<List<Pet>>>any()
        );
        assertEquals(1, pets.size());
        assertEqualsPets(pet, pets.get(0));
    }

    @Test
    void getPetsByStatus_shouldPass_forPendingStatus() {
        /*********** Setup ************/
        String status = "pending";
        Pet pet = preparePetForStatus(status);
        ResponseEntity<List<Pet>> myEntity = new ResponseEntity<List<Pet>>(List.of(pet), HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
            ArgumentMatchers.eq(GET_PETS_BY_STATUS_API_URI_MOCK + "?status=" + status),
            ArgumentMatchers.eq(HttpMethod.GET),
            ArgumentMatchers.<HttpEntity<List<Pet>>>any(),
            ArgumentMatchers.<ParameterizedTypeReference<List<Pet>>>any())
        ).thenReturn(myEntity);
        /*********** Execute ************/
        List<Pet> pets = petService.getPetsByStatus(status);
        /*********** Verify/Assertions ************/
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(
            ArgumentMatchers.eq(GET_PETS_BY_STATUS_API_URI_MOCK + "?status=" + status),
            ArgumentMatchers.eq(HttpMethod.GET),
            ArgumentMatchers.<HttpEntity<List<Pet>>>any(),
            ArgumentMatchers.<ParameterizedTypeReference<List<Pet>>>any()
        );
        assertEquals(1, pets.size());
        assertEqualsPets(pet, pets.get(0));
    }

    @Test
    void getPetsByStatus_shouldPass_forSoldStatus() {
        /*********** Setup ************/
        String status = "sold";
        Pet pet = preparePetForStatus(status);
        ResponseEntity<List<Pet>> myEntity = new ResponseEntity<List<Pet>>(List.of(pet), HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
            ArgumentMatchers.eq(GET_PETS_BY_STATUS_API_URI_MOCK + "?status=" + status),
            ArgumentMatchers.eq(HttpMethod.GET),
            ArgumentMatchers.<HttpEntity<List<Pet>>>any(),
            ArgumentMatchers.<ParameterizedTypeReference<List<Pet>>>any())
        ).thenReturn(myEntity);
        /*********** Execute ************/
        List<Pet> pets = petService.getPetsByStatus(status);
        /*********** Verify/Assertions ************/
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(
            ArgumentMatchers.eq(GET_PETS_BY_STATUS_API_URI_MOCK + "?status=" + status),
            ArgumentMatchers.eq(HttpMethod.GET),
            ArgumentMatchers.<HttpEntity<List<Pet>>>any(),
            ArgumentMatchers.<ParameterizedTypeReference<List<Pet>>>any()
        );
        assertEquals(1, pets.size());
        assertEqualsPets(pet, pets.get(0));
    }

    @Test
    void getPetsByStatus_shouldThrow_PetStoreApiException() {
        /*********** Setup ************/
        String status = "sold";
        /*********** Execute ************/
        PetStoreApiException exception = Assertions.assertThrows(PetStoreApiException.class, () -> {
            Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.<HttpEntity<List<Pet>>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Pet>>>any())
            ).thenThrow(RestClientException.class);
            petService.getPetsByStatus(status);
        });
        /*********** Verify/Assertions ************/
        assertEquals("1005-Error while calling getPetsByStatus API from PetStore", exception.getMessage());
    }

    private Pet preparePetForStatus(String status) {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("jack");
        pet.setStatus(status);
        pet.setPhotoUrls(List.of("https://photo.url.one", "https://photo.url.two"));

        Category category = new Category();
        category.setId(1L);
        category.setName("cat1");
        pet.setCategory(category);

        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("tag1");
        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("tag2");
        pet.setTags(List.of(tag1, tag2));

        return pet;
    }

    private void assertEqualsPets(Pet expectedPet, Pet actualPet) {
        assertEquals(expectedPet.getId(), actualPet.getId());
        assertEquals(expectedPet.getName(), actualPet.getName());
        assertEquals(expectedPet.getStatus(), actualPet.getStatus());
        assertEquals(expectedPet.getPhotoUrls(), actualPet.getPhotoUrls());
        assertEquals(expectedPet.getCategory().getId(), actualPet.getCategory().getId());
        assertEquals(expectedPet.getCategory().getName(), actualPet.getCategory().getName());
        assertEquals(expectedPet.getTags().size(), actualPet.getTags().size());
        assertEquals(expectedPet.getTags().get(0).getId(), actualPet.getTags().get(0).getId());
        assertEquals(expectedPet.getTags().get(0).getName(), actualPet.getTags().get(0).getName());
    }
}