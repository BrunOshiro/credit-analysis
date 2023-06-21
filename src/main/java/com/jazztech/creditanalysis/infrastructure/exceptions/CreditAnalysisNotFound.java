package com.jazztech.creditanalysis.infrastructure.exceptions;

public class CreditAnalysisNotFound extends RuntimeException {
    public CreditAnalysisNotFound(String message) {
        super(message);
    }
}
