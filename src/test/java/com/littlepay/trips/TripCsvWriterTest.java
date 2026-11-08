package com.littlepay.trips;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TripCsvWriterTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void writesCompletedCancelledAndIncompleteTrips() throws Exception {
        Tap completedTapOn = tap(1, 0, TapType.ON, "Stop1");
        Tap completedTapOff = tap(2, 5, TapType.OFF, "Stop2");
        Tap cancelledTapOn = tap(3, 10, TapType.ON, "Stop3");
        Tap cancelledTapOff = tap(4, 12, TapType.OFF, "Stop3");
        Tap incompleteTapOn = tap(5, 15, TapType.ON, "Stop2");
        Path outputFile = temporaryDirectory.resolve("trips.csv");

        new TripCsvWriter().write(outputFile, List.of(
                new Trip(completedTapOn, Optional.of(completedTapOff), 325, TripStatus.COMPLETED),
                new Trip(cancelledTapOn, Optional.of(cancelledTapOff), 0, TripStatus.CANCELLED),
                new Trip(incompleteTapOn, Optional.empty(), 550, TripStatus.INCOMPLETE)));

        assertEquals(String.join("\n",
                "Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status",
                "22-01-2023 08:00:00,22-01-2023 08:05:00,300,Stop1,Stop2,$3.25,Company1,Bus1,TestPan1,COMPLETED",
                "22-01-2023 08:10:00,22-01-2023 08:12:00,120,Stop3,Stop3,$0.00,Company1,Bus1,TestPan1,CANCELLED",
                "22-01-2023 08:15:00,,0,Stop2,,$5.50,Company1,Bus1,TestPan1,INCOMPLETE") + "\n",
                Files.readString(outputFile));
    }

    private Tap tap(long id, int minute, TapType type, String stopId) {
        return new Tap(
                id,
                LocalDateTime.of(2023, 1, 22, 8, 0).plusMinutes(minute),
                type,
                stopId,
                "Company1",
                "Bus1",
                "TestPan1");
    }
}
