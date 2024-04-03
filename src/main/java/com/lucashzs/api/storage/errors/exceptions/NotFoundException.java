package com.lucashzs.api.storage.errors.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException (String errorMessage){
        super(errorMessage);
    }
}
