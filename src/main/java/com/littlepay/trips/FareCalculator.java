package com.littlepay.trips;

import java.util.Map;

public final class FareCalculator {
    private static final Map<StopPair, Integer> FARES = Map.of(
            StopPair.between("Stop1", "Stop2"), 325,
            StopPair.between("Stop2", "Stop3"), 550,
            StopPair.between("Stop1", "Stop3"), 730);

    public int chargeFor(String fromStopId, String toStopId) {
        if (fromStopId.equals(toStopId)) {
            return 0;
        }

        Integer fare = FARES.get(StopPair.between(fromStopId, toStopId));
        if (fare == null) {
            throw new UnknownFareException("No fare is configured between " + fromStopId + " and " + toStopId);
        }
        return fare;
    }

    public int maximumChargeFrom(String fromStopId) {
        return FARES.entrySet().stream()
                .filter(entry -> entry.getKey().includes(fromStopId))
                .mapToInt(Map.Entry::getValue)
                .max()
                .orElseThrow(() -> new UnknownFareException("No fare is configured from " + fromStopId));
    }

    private record StopPair(String firstStopId, String secondStopId) {
        private static StopPair between(String firstStopId, String secondStopId) {
            if (firstStopId.compareTo(secondStopId) <= 0) {
                return new StopPair(firstStopId, secondStopId);
            }
            return new StopPair(secondStopId, firstStopId);
        }

        private boolean includes(String stopId) {
            return firstStopId.equals(stopId) || secondStopId.equals(stopId);
        }
    }
}
