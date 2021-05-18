package com.ajira.test.fileoperations.exceptions;

public class InvalidConfigurationFileException extends RuntimeException{

    public InvalidConfigurationFileException(String message){
        super(message);
    }
}
