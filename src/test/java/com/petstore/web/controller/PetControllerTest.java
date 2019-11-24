package com.petstore.web.controller;

import com.petstore.config.ErrorMessageSourceConfig;
import com.petstore.dto.Category;
import com.petstore.dto.Pet;
import com.petstore.dto.Tag;
import com.petstore.exception.PetStoreApiException;
import com.petstore.service.PetService;
import com.petstore.util.ErrorGeneratorInitializer;
import com.petstore.web.exception.PetStoreManagementExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import java.util.List;

import static com.petstore.constant.Status.CLIENT_ERROR;
import static com.petstore.constant.Status.FAIL;
import static com.petstore.constant.Status.SUCCESS;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@ExtendWith(SpringExtension.class)
@Import({
    SpringConstraintValidatorFactory.class,
    LocalValidatorFactoryBean.class,
    ErrorMessageSourceConfig.class,
    ErrorGeneratorInitializer.class,
    PetStoreManagementExceptionHandler.class})
@ContextConfiguration
@WebAppConfiguration
public class PetControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PetController petController;

    @MockBean
    private PetService petService;

    @Autowired
    private PetStoreManagementExceptionHandler petStoreManagementExceptionHandler;

    @Autowired
    private LocalValidatorFactoryBean validator;

    /**
     * Setup for before each test case
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = standaloneSetup(petController)
            .setValidator(validator)
            .setControllerAdvice(petStoreManagementExceptionHandler)
            .build();
    }

    /**
     * @throws Exception
     * @since 0.0.1
     */
    @Test
    public void getPetsByStatus_shouldPass_forAvailableStatus() throws Exception {
        /*********** Setup ************/
        String status = "available";
        Pet pet = preparePetForStatus(status);
        Mockito.when(petService.getPetsByStatus(status)).thenReturn(List.of(pet));

        /*********** Execute ************/
        mockMvc.perform(get("/api/v1/pet/byStatus?status=" + status)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(SUCCESS.name())))
            .andExpect(jsonPath("$.code", is(200)))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].id", is(pet.getId())))
            .andExpect(jsonPath("$.data[0].name", is(pet.getName())))
            .andExpect(jsonPath("$.data[0].status", is(status)))
            .andExpect(jsonPath("$.data[0].photoUrls", hasSize(pet.getPhotoUrls().size())))
            .andExpect(jsonPath("$.data[0].tags", hasSize(pet.getTags().size())))
            .andExpect(jsonPath("$.data[0].category.id", is(pet.getCategory().getId())))
            .andExpect(jsonPath("$.data[0].category.name", is(pet.getCategory().getName())))
            .andExpect(jsonPath("$.errors").doesNotExist());

        /*********** Verify/Assertions ************/
        verify(petService, times(1)).getPetsByStatus(status);
        verifyNoMoreInteractions(petService);
    }

    /**
     * @throws Exception
     * @since 0.0.1
     */
    @Test
    public void getPetsByStatus_shouldPass_forPendingStatus() throws Exception {
        /*********** Setup ************/
        String status = "pending";
        Pet pet = preparePetForStatus(status);
        Mockito.when(petService.getPetsByStatus(status)).thenReturn(List.of(pet));

        /*********** Execute ************/
        mockMvc.perform(get("/api/v1/pet/byStatus?status=" + status)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(SUCCESS.name())))
            .andExpect(jsonPath("$.code", is(200)))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].id", is(pet.getId())))
            .andExpect(jsonPath("$.data[0].name", is(pet.getName())))
            .andExpect(jsonPath("$.data[0].status", is(status)))
            .andExpect(jsonPath("$.data[0].photoUrls", hasSize(pet.getPhotoUrls().size())))
            .andExpect(jsonPath("$.data[0].tags", hasSize(pet.getTags().size())))
            .andExpect(jsonPath("$.data[0].category.id", is(pet.getCategory().getId())))
            .andExpect(jsonPath("$.data[0].category.name", is(pet.getCategory().getName())))
            .andExpect(jsonPath("$.errors").doesNotExist());

        /*********** Verify/Assertions ************/
        verify(petService, times(1)).getPetsByStatus(status);
        verifyNoMoreInteractions(petService);
    }

    @Test
    public void getPetsByStatus_shouldPass_forSoldStatus() throws Exception {
        /*********** Setup ************/
        String status = "sold";
        Pet pet = preparePetForStatus(status);
        Mockito.when(petService.getPetsByStatus(status)).thenReturn(List.of(pet));

        /*********** Execute ************/
        mockMvc.perform(get("/api/v1/pet/byStatus?status=" + status)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(SUCCESS.name())))
            .andExpect(jsonPath("$.code", is(200)))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.data[0].id", is(pet.getId())))
            .andExpect(jsonPath("$.data[0].name", is(pet.getName())))
            .andExpect(jsonPath("$.data[0].status", is(status)))
            .andExpect(jsonPath("$.data[0].photoUrls", hasSize(pet.getPhotoUrls().size())))
            .andExpect(jsonPath("$.data[0].tags", hasSize(pet.getTags().size())))
            .andExpect(jsonPath("$.data[0].category.id", is(pet.getCategory().getId())))
            .andExpect(jsonPath("$.data[0].category.name", is(pet.getCategory().getName())))
            .andExpect(jsonPath("$.errors").doesNotExist());

        /*********** Verify/Assertions ************/
        verify(petService, times(1)).getPetsByStatus(status);
        verifyNoMoreInteractions(petService);
    }

    /**
     * parameter validation is not possible with standalone setup
     *
     * @throws Exception
     */
    @Disabled
    @Test
    public void getPetsByStatus_shouldFail_forInvalidStatus() throws Exception {
        /*********** Setup ************/
        String status = "invalid";

        /*********** Execute ************/
        mockMvc.perform(get("/api/v1/pet/byStatus?status=" + status)
            .contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is(CLIENT_ERROR.name())))
            .andExpect(jsonPath("$.code", is(400)))
            .andExpect(jsonPath("$.data").doesNotExist())
            .andExpect(jsonPath("$.errors", hasSize(1)))
            .andExpect(jsonPath("$.errors[0].code", is("1004")))
            .andExpect(jsonPath("$.errors[0].message", is("Invalid pet status")));

        /*********** Verify/Assertions ************/
        verify(petService, never()).getPetsByStatus(status);
    }

    @Test
    void getPetsByStatus_shouldThrow_PetStoreApiException() throws Exception {
        /*********** Setup ************/
        String status = "sold";
        Mockito.when(petService.getPetsByStatus(status)).thenThrow(new PetStoreApiException("getPetsByStatus",
            new RuntimeException()));
        /*********** Execute ************/
        mockMvc.perform(get("/api/v1/pet/byStatus?status=" + status)
            .contentType(APPLICATION_JSON))
            .andExpect(status().is5xxServerError())
            .andExpect(jsonPath("$.status", is(FAIL.name())))
            .andExpect(jsonPath("$.code", is(500)))
            .andExpect(jsonPath("$.data").doesNotExist())
            .andExpect(jsonPath("$.errors", hasSize(1)))
            .andExpect(jsonPath("$.errors[0].code", is("1005")))
            .andExpect(jsonPath("$.errors[0].message", is("Error while calling getPetsByStatus API from PetStore")));

    }

    private Pet preparePetForStatus(String status) {
        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("jack");
        pet.setStatus(status);
        pet.setPhotoUrls(List.of("https://photo.url.one", "https://photo.url.two"));

        Category category = new Category();
        category.setId(1);
        category.setName("cat1");
        pet.setCategory(category);

        Tag tag1 = new Tag();
        tag1.setId(1);
        tag1.setName("tag1");
        Tag tag2 = new Tag();
        tag2.setId(1);
        tag2.setName("tag2");
        pet.setTags(List.of(tag1, tag2));

        return pet;
    }
}
