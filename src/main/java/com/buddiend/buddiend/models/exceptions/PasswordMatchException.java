package com.buddiend.buddiend.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class PasswordMatchException extends RuntimeException {
    public PasswordMatchException(String message) { super (message); }
}
