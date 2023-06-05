package com.jazztech.creditanalysis.infrastructure.exceptions;

import java.util.UUID;

public class ClientNotFound extends RuntimeException {
    public ClientNotFound(String message, UUID clientId) {
        super(String.format(message, clientId));
    }
}
