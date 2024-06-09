package com.example.travelly.Controller;

import com.example.travelly.Exceptions.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        log.info("*** Inside handleMethodArgumentNotValidException exception handler ***");
        Map<String, String> map = new HashMap<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            map.put(error.getField(),error.getDefaultMessage());
        }
        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomizedException.class)
    public ResponseEntity handleCustomizedException(CustomizedException ex){
        log.info("*** Inside handleCustomizedException controller ***");
        Map<String, String> map = new HashMap<>();
        map.put(ex.getMessage(), ex.getSubMessage());
        return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex){
        log.info("*** Inside handle Exception controller ***");
        ex.printStackTrace();
       Map<String, String> map = new HashMap<>();
       map.put("Message", ex.getMessage());
       return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
