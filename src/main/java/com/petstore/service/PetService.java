package com.petstore.service;

import com.petstore.dto.Pet;

import java.util.List;

/**
 * Bundles all APIs for Pet.
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
public interface PetService {
    List<Pet> getPetsByStatus(String status);

    List<Pet> getPetsByStatus(String status, boolean mockReponse);
}
