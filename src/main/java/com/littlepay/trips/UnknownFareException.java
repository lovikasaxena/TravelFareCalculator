package com.littlepay.trips;

public final class UnknownFareException extends IllegalArgumentException {
    public UnknownFareException(String message) {
        super(message);
    }
}
