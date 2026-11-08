package com.littlepay.trips;

import java.nio.file.Path;
import java.util.List;

public final class TravelFareCalculatorApplication {
    private TravelFareCalculatorApplication() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: <input taps.csv> <output trips.csv>");
        }

        TapCsvReader tapCsvReader = new TapCsvReader();
        TripMatcher tripMatcher = new TripMatcher(new FareCalculator());
        TripCsvWriter tripCsvWriter = new TripCsvWriter();

        List<Tap> taps = tapCsvReader.read(Path.of(args[0]));
        List<Trip> trips = tripMatcher.createTrips(taps);
        tripCsvWriter.write(Path.of(args[1]), trips);
    }
}
