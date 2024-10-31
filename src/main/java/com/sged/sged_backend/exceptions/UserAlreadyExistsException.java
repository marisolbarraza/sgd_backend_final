package com.sged.sged_backend.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String mensaje) {
        super(mensaje);
    }
}
