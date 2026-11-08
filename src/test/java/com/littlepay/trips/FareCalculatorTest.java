package com.littlepay.trips;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FareCalculatorTest {
    private final FareCalculator fareCalculator = new FareCalculator();

    @Test
    void charges325CentsFromStop1ToStop2() {
        assertEquals(325, fareCalculator.chargeFor("Stop1", "Stop2"));
    }

    @Test
    void charges325CentsFromStop2ToStop1() {
        assertEquals(325, fareCalculator.chargeFor("Stop2", "Stop1"));
    }

    @Test
    void charges550CentsFromStop2ToStop3() {
        assertEquals(550, fareCalculator.chargeFor("Stop2", "Stop3"));
    }

    @Test
    void charges550CentsFromStop3ToStop2() {
        assertEquals(550, fareCalculator.chargeFor("Stop3", "Stop2"));
    }

    @Test
    void charges730CentsFromStop1ToStop3() {
        assertEquals(730, fareCalculator.chargeFor("Stop1", "Stop3"));
    }

    @Test
    void charges730CentsFromStop3ToStop1() {
        assertEquals(730, fareCalculator.chargeFor("Stop3", "Stop1"));
    }

    @Test
    void chargesNothingForACancelledTripAtStop1() {
        assertEquals(0, fareCalculator.chargeFor("Stop1", "Stop1"));
    }

    @Test
    void chargesNothingForACancelledTripAtStop2() {
        assertEquals(0, fareCalculator.chargeFor("Stop2", "Stop2"));
    }

    @Test
    void chargesNothingForACancelledTripAtStop3() {
        assertEquals(0, fareCalculator.chargeFor("Stop3", "Stop3"));
    }

    @Test
    void charges730CentsForAnIncompleteTripFromStop1() {
        assertEquals(730, fareCalculator.maximumChargeFrom("Stop1"));
    }

    @Test
    void charges550CentsForAnIncompleteTripFromStop2() {
        assertEquals(550, fareCalculator.maximumChargeFrom("Stop2"));
    }

    @Test
    void charges730CentsForAnIncompleteTripFromStop3() {
        assertEquals(730, fareCalculator.maximumChargeFrom("Stop3"));
    }
}
