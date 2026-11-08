package com.littlepay.trips;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class TripCsvWriter {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final String HEADER = "Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status";

    public void write(Path outputFile, List<Trip> trips) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        trips.forEach(trip -> lines.add(formatTrip(trip)));
        Files.write(outputFile, lines);
    }

    private String formatTrip(Trip trip) {
        Tap tapOn = trip.tapOn();
        Tap tapOff = trip.tapOff().orElse(null);

        String finished = tapOff == null ? "" : DATE_TIME_FORMAT.format(tapOff.dateTimeUtc());
        long durationSeconds = tapOff == null ? 0 : Duration.between(tapOn.dateTimeUtc(), tapOff.dateTimeUtc()).getSeconds();
        String toStopId = tapOff == null ? "" : tapOff.stopId();

        return String.join(",",
                DATE_TIME_FORMAT.format(tapOn.dateTimeUtc()),
                finished,
                Long.toString(durationSeconds),
                tapOn.stopId(),
                toStopId,
                formatCharge(trip.chargeInCents()),
                tapOn.companyId(),
                tapOn.busId(),
                tapOn.pan(),
                trip.status().name());
    }

    private String formatCharge(int chargeInCents) {
        return String.format(Locale.ROOT, "$%.2f", chargeInCents / 100.0);
    }
}
