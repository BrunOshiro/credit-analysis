package com.jazztech.creditanalysis.infrastructure.exceptions;

public class ClientNotFound extends RuntimeException {
    public ClientNotFound(String message, String clientId) {
        super(String.format(message, clientId));
    }
}
