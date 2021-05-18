package com.ajira.test.fileoperations.exceptions;

public class OperationNotSupportedException extends RuntimeException{

    public OperationNotSupportedException(String message){
        super(message);
    }
}
