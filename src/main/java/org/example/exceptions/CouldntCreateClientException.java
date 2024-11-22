package org.example.exceptions;

public class CouldntCreateClientException extends RuntimeException {
    public CouldntCreateClientException(String message) {
        super(message);
    }
}
