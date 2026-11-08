package com.littlepay.trips;

import java.time.LocalDateTime;

public record Tap(
        long id,
        LocalDateTime dateTimeUtc,
        TapType type,
        String stopId,
        String companyId,
        String busId,
        String pan) {
}
