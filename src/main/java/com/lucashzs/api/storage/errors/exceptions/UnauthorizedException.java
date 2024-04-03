package com.lucashzs.api.storage.errors.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException (String errorMessage){
        super(errorMessage);
    }
}
