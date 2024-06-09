package com.example.travelly.Exceptions;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CustomizedException extends RuntimeException{
    String message;
    String subMessage;

    public CustomizedException(String message, String subMessage){
        this.message=message;
        this.subMessage=subMessage;
    }


}
