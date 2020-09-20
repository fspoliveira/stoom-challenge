package br.com.stoom.challenge.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.stoom.challenge.exception.GoogleApiInvalidAddressInfo;
import br.com.stoom.challenge.model.ErrorModel;
import br.com.stoom.challenge.exception.StAddressNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {StAddressNotFoundException.class, EmptyResultDataAccessException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
            ex,
            ErrorModel.builder().message("Address not found").build(),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND,
            request);
    }

    @ExceptionHandler(value = {GoogleApiInvalidAddressInfo.class})
    protected ResponseEntity<Object> handleInvalidAddressSentToGoogle(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
            ex,
            ErrorModel.builder().message("Invalid Address data!").build(),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request);
    }

    @ExceptionHandler(value = {InvalidDataAccessApiUsageException.class})
    protected ResponseEntity<Object> invalidQueryStringField(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
            ex,
            ErrorModel.builder()
                .message("Invalid field name passed in query string! The possible values are [zipcode, streetName, city, latitude, longitude, country, neighbourhood, number, state, complement]. Bear in mind that it is case sensitive.")
                .build(),
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            request);
    }
}
