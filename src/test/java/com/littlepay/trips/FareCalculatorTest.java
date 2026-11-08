package com.littlepay.trips;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FareCalculatorTest {
    private final FareCalculator fareCalculator = new FareCalculator();

    @Test
    void calculatesCompletedTripFaresInBothDirections() {
        assertEquals(325, fareCalculator.chargeFor("Stop1", "Stop2"));
        assertEquals(325, fareCalculator.chargeFor("Stop2", "Stop1"));
        assertEquals(550, fareCalculator.chargeFor("Stop2", "Stop3"));
        assertEquals(550, fareCalculator.chargeFor("Stop3", "Stop2"));
        assertEquals(730, fareCalculator.chargeFor("Stop1", "Stop3"));
        assertEquals(730, fareCalculator.chargeFor("Stop3", "Stop1"));
    }

    @Test
    void chargesNothingForACancelledTrip() {
        assertEquals(0, fareCalculator.chargeFor("Stop1", "Stop1"));
    }

    @Test
    void calculatesTheMaximumIncompleteTripFare() {
        assertEquals(730, fareCalculator.maximumChargeFrom("Stop1"));
        assertEquals(550, fareCalculator.maximumChargeFrom("Stop2"));
        assertEquals(730, fareCalculator.maximumChargeFrom("Stop3"));
    }

}
