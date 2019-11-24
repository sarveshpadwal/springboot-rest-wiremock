package com.petstore.exception;

import com.petstore.util.ErrorGenerator;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
public class PetStoreApiException extends PetStoreManagementException {
    private static final long serialVersionUID = 1L;

    public PetStoreApiException(String api, Throwable cause) {
        super(ErrorGenerator.generateForCodeWithArguments("1005", api), cause);
    }
}
