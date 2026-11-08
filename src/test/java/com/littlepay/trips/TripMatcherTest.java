package com.littlepay.trips;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TripMatcherTest {
    private static final LocalDateTime START_TIME = LocalDateTime.of(2023, 1, 22, 8, 0);
    private final TripMatcher tripMatcher = new TripMatcher(new FareCalculator());

    @Test
    void createsACompletedTripForMatchingTaps() {
        Tap tapOn = tap(1, 0, TapType.ON, "Stop1", "Bus1", "Pan1");
        Tap tapOff = tap(2, 5, TapType.OFF, "Stop2", "Bus1", "Pan1");

        assertEquals(List.of(completedTrip(tapOn, tapOff, 325)), tripMatcher.createTrips(List.of(tapOn, tapOff)));
    }

    @Test
    void createsACancelledTripForTapsAtTheSameStop() {
        Tap tapOn = tap(1, 0, TapType.ON, "Stop1", "Bus1", "Pan1");
        Tap tapOff = tap(2, 5, TapType.OFF, "Stop1", "Bus1", "Pan1");

        assertEquals(List.of(cancelledTrip(tapOn, tapOff)), tripMatcher.createTrips(List.of(tapOn, tapOff)));
    }

    @Test
    void createsAnIncompleteTripForAnUnmatchedTapOn() {
        Tap tapOn = tap(1, 0, TapType.ON, "Stop2", "Bus1", "Pan1");

        assertEquals(List.of(incompleteTrip(tapOn, 550)), tripMatcher.createTrips(List.of(tapOn)));
    }

    @Test
    void ignoresAnUnmatchedTapOff() {
        Tap tapOff = tap(1, 0, TapType.OFF, "Stop1", "Bus1", "Pan1");

        assertEquals(List.of(), tripMatcher.createTrips(List.of(tapOff)));
    }

    @Test
    void doesNotMatchTapsFromDifferentBuses() {
        Tap firstTapOn = tap(1, 0, TapType.ON, "Stop3", "Bus36", "Pan1");
        Tap secondTapOn = tap(2, 5, TapType.ON, "Stop1", "Bus37", "Pan1");
        Tap tapOff = tap(3, 10, TapType.OFF, "Stop1", "Bus37", "Pan1");

        assertEquals(
                List.of(incompleteTrip(firstTapOn, 730), cancelledTrip(secondTapOn, tapOff)),
                tripMatcher.createTrips(List.of(firstTapOn, secondTapOn, tapOff)));
    }

    @Test
    void doesNotMatchTapsFromDifferentCompanies() {
        Tap firstTapOn = tap(1, 0, TapType.ON, "Stop3", "Company1", "Bus1", "Pan1");
        Tap secondTapOn = tap(2, 5, TapType.ON, "Stop1", "Company2", "Bus1", "Pan1");
        Tap tapOff = tap(3, 10, TapType.OFF, "Stop1", "Company2", "Bus1", "Pan1");

        assertEquals(
                List.of(incompleteTrip(firstTapOn, 730), cancelledTrip(secondTapOn, tapOff)),
                tripMatcher.createTrips(List.of(firstTapOn, secondTapOn, tapOff)));
    }

    @Test
    void closesThePreviousTripWhenASecondTapOnArrives() {
        Tap firstTapOn = tap(1, 0, TapType.ON, "Stop1", "Bus1", "Pan1");
        Tap secondTapOn = tap(2, 5, TapType.ON, "Stop2", "Bus1", "Pan1");
        Tap tapOff = tap(3, 10, TapType.OFF, "Stop3", "Bus1", "Pan1");

        assertEquals(
                List.of(incompleteTrip(firstTapOn, 730), completedTrip(secondTapOn, tapOff, 550)),
                tripMatcher.createTrips(List.of(firstTapOn, secondTapOn, tapOff)));
    }

    @Test
    void sortsInputTapsByTimeBeforeMatchingThem() {
        Tap tapOn = tap(1, 0, TapType.ON, "Stop1", "Bus1", "Pan1");
        Tap tapOff = tap(2, 5, TapType.OFF, "Stop2", "Bus1", "Pan1");

        assertEquals(List.of(completedTrip(tapOn, tapOff, 325)), tripMatcher.createTrips(List.of(tapOff, tapOn)));
    }

    @Test
    void usesTapIdToOrderTapsWithTheSameTime() {
        Tap firstTapOn = tap(1, 0, TapType.ON, "Stop1", "Bus1", "Pan1");
        Tap secondTapOn = tap(2, 0, TapType.ON, "Stop2", "Bus1", "Pan1");
        Tap tapOff = tap(3, 5, TapType.OFF, "Stop3", "Bus1", "Pan1");

        assertEquals(
                List.of(incompleteTrip(firstTapOn, 730), completedTrip(secondTapOn, tapOff, 550)),
                tripMatcher.createTrips(List.of(tapOff, secondTapOn, firstTapOn)));
    }

    @Test
    void ordersTripsByTapOnTimeThenTapId() {
        Tap laterTapOn = tap(2, 10, TapType.ON, "Stop2", "Bus1", "Pan2");
        Tap earlierTapOn = tap(1, 0, TapType.ON, "Stop1", "Bus1", "Pan1");
        Tap earlierTapOff = tap(3, 5, TapType.OFF, "Stop2", "Bus1", "Pan1");
        Tap laterTapOff = tap(4, 15, TapType.OFF, "Stop3", "Bus1", "Pan2");

        assertEquals(
                List.of(completedTrip(earlierTapOn, earlierTapOff, 325), completedTrip(laterTapOn, laterTapOff, 550)),
                tripMatcher.createTrips(List.of(laterTapOff, laterTapOn, earlierTapOff, earlierTapOn)));
    }

    private Tap tap(long id, int minute, TapType type, String stopId, String busId, String pan) {
        return tap(id, minute, type, stopId, "Company1", busId, pan);
    }

    private Tap tap(long id, int minute, TapType type, String stopId, String companyId, String busId, String pan) {
        return new Tap(id, START_TIME.plusMinutes(minute), type, stopId, companyId, busId, pan);
    }

    private Trip completedTrip(Tap tapOn, Tap tapOff, int chargeInCents) {
        return new Trip(tapOn, Optional.of(tapOff), chargeInCents, TripStatus.COMPLETED);
    }

    private Trip cancelledTrip(Tap tapOn, Tap tapOff) {
        return new Trip(tapOn, Optional.of(tapOff), 0, TripStatus.CANCELLED);
    }

    private Trip incompleteTrip(Tap tapOn, int chargeInCents) {
        return new Trip(tapOn, Optional.empty(), chargeInCents, TripStatus.INCOMPLETE);
    }
}
