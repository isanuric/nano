package com.isanuric.nano.exception;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.text.SimpleDateFormat;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        final var jsonObject = new JSONObject();
        final var localizedMessage = ex.getLocalizedMessage();
        jsonObject.put("error", localizedMessage);
        jsonObject.put("status", status);
        return new ResponseEntity<>(jsonObject, BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String generallMessage = "Malformed JSON request";
        return new ResponseEntity<>(new ApiException(BAD_REQUEST, generallMessage, ex), status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        String generalMessage = "404, Not Found";
        ApiException apiError = new ApiException(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(new ApiException(BAD_REQUEST, generalMessage, ex), NOT_FOUND);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIApiIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ApiException(BAD_REQUEST, "400, Bad Request", ex), BAD_REQUEST);
    }
}
