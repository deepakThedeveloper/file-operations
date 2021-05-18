package com.ajira.test.fileoperations.exceptions;

public class InvalidFilePathException extends RuntimeException{

    public InvalidFilePathException(String message){
        super(message);
    }
}
