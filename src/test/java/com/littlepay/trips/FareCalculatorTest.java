package com.littlepay.trips;

public final class FareCalculatorTest {
    private final FareCalculator fareCalculator = new FareCalculator();

    public static void main(String[] args) {
        FareCalculatorTest test = new FareCalculatorTest();
        test.calculatesCompletedTripFaresInBothDirections();
        test.chargesNothingForACancelledTrip();
        test.calculatesTheMaximumIncompleteTripFare();
    }

    private void calculatesCompletedTripFaresInBothDirections() {
        assertEquals(325, fareCalculator.chargeFor("Stop1", "Stop2"));
        assertEquals(325, fareCalculator.chargeFor("Stop2", "Stop1"));
        assertEquals(550, fareCalculator.chargeFor("Stop2", "Stop3"));
        assertEquals(550, fareCalculator.chargeFor("Stop3", "Stop2"));
        assertEquals(730, fareCalculator.chargeFor("Stop1", "Stop3"));
        assertEquals(730, fareCalculator.chargeFor("Stop3", "Stop1"));
    }

    private void chargesNothingForACancelledTrip() {
        assertEquals(0, fareCalculator.chargeFor("Stop1", "Stop1"));
    }

    private void calculatesTheMaximumIncompleteTripFare() {
        assertEquals(730, fareCalculator.maximumChargeFrom("Stop1"));
        assertEquals(550, fareCalculator.maximumChargeFrom("Stop2"));
        assertEquals(730, fareCalculator.maximumChargeFrom("Stop3"));
    }

    private void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " cents but was " + actual + " cents");
        }
    }
}
