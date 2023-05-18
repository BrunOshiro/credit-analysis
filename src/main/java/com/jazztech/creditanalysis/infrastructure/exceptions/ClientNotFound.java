package com.jazztech.creditanalysis.infrastructure.exceptions;

import java.util.UUID;

public class ClientNotFound extends Throwable {
    public ClientNotFound(String s, UUID clientId) {
    }
}
