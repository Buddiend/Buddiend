package com.buddiend.buddiend.models.exceptions;

public class LanguageNotFoundException extends RuntimeException{
    public LanguageNotFoundException(Long id) {
        super(String.format("Language with id: %d is not found", id));
    }
}
