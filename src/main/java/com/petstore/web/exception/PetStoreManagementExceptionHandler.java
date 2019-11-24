package com.petstore.web.exception;

import com.petstore.constant.Status;
import com.petstore.dto.Response;
import com.petstore.exception.ErrorDetails;
import com.petstore.exception.PetStoreManagementException;
import com.petstore.util.ErrorGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

/**
 * Exception handler to handle API specific exceptions.
 *
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@RestControllerAdvice
public class PetStoreManagementExceptionHandler extends CommonResponseEntityExceptionHandler {

    @SuppressWarnings({"rawtypes"})
    @ExceptionHandler({ConstraintViolationException.class})
    public final ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException ax,
                                                                             WebRequest request) {
        log.error("<<ConstraintViolationException>>", ax);
        ErrorDetails[] arr =
            ax.getConstraintViolations().stream()
                .map(error -> ErrorGenerator.generateForCode(error.getMessage()))
                .toArray(ErrorDetails[]::new);
        Response<Void> errorResponse = new Response<Void>(Status.CLIENT_ERROR, HttpStatus.BAD_REQUEST.value(), arr);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({"rawtypes"})
    @ExceptionHandler({PetStoreManagementException.class})
    public final ResponseEntity<Response> handlePetStoreManagementException(PetStoreManagementException ax,
                                                                        WebRequest request) {
        log.error("<<PetStoreManagementException>>", ax);
        Response errorResponse = new Response<Void>(Status.FAIL, HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ax.getError());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SuppressWarnings({"rawtypes"})
    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Response> handleExceptions(Exception ex, WebRequest request) {
        log.error("<<Exceptions>>", ex);
        Response errorResponse = new Response<Void>(Status.FAIL, HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ErrorGenerator.generateForCode("1000"));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
