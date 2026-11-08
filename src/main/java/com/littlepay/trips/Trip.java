package com.littlepay.trips;

import java.util.Optional;

public record Trip(
        Tap tapOn,
        Optional<Tap> tapOff,
        int chargeInCents,
        TripStatus status) {
}
