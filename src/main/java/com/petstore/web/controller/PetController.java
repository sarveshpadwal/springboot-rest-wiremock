package com.petstore.web.controller;

import com.petstore.constant.Status;
import com.petstore.dto.Pet;
import com.petstore.dto.Response;
import com.petstore.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Bundles all APIs for Pet.
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Api(tags = "All APIs for Pet")
@RestController
@RequestMapping("/api/v1/pet")
@Validated
public class PetController {

    @Autowired
    private PetService petService;

    @ApiOperation(value = "Get list of Pets by status", produces = "application/json")
    @GetMapping("/byStatus")
    public Response<List<Pet>> getPetsByStatus(
        @ApiParam(value = "Pet status for which all pets will be retrieved",
            allowableValues = "available,pending,sold", required = true)
        @Valid @Pattern(regexp = "available|pending|sold", message = "1004")
        @RequestParam(value = "status") String status) {
        List<Pet> pets = petService.getPetsByStatus(status);
        return new Response<>(Status.SUCCESS, HttpStatus.OK.value(), pets);
    }
}