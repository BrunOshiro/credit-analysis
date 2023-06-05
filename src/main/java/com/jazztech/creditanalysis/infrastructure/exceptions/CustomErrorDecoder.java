package com.jazztech.creditanalysis.infrastructure.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    private static final int HTTP_NOT_FOUND = 404;

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HTTP_NOT_FOUND) {
            return new ClientNotFound("Client not found for the ID %s", extractClientIdFromUrl(response.request().url()));
        }
        return new Default().decode(methodKey, response);
    }

    private String extractClientIdFromUrl(String url) {
        final String[] urlParts = url.split("/");
        return urlParts[urlParts.length - 1];
    }
}
