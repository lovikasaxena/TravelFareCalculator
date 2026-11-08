package com.littlepay.trips;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TravelFareCalculatorApplicationTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void createsTheExpectedTripsForTheExampleInput() throws Exception {
        Path inputFile = Path.of("examples/taps.csv");
        Path outputFile = temporaryDirectory.resolve("trips.csv");

        TravelFareCalculatorApplication.main(new String[]{inputFile.toString(), outputFile.toString()});

        assertEquals(Files.readString(Path.of("examples/trips.csv")), Files.readString(outputFile));
    }
}
