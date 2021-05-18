package com.ajira.test.fileoperations.exceptions;

public class InvalidInputFileException extends RuntimeException{

    public InvalidInputFileException(String message){
        super(message);
    }
}
