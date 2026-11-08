package com.littlepay.trips;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class TripMatcher {
    private final FareCalculator fareCalculator;

    public TripMatcher(FareCalculator fareCalculator) {
        this.fareCalculator = fareCalculator;
    }

    public List<Trip> createTrips(List<Tap> taps) {
        validateUniqueTapIds(taps);

        Map<TripKey, Tap> openTaps = new HashMap<>();
        List<Trip> trips = new ArrayList<>();

        taps.stream()
                .sorted(Comparator.comparing(Tap::dateTimeUtc).thenComparing(Tap::id))
                .forEach(tap -> processTap(tap, openTaps, trips));

        openTaps.values().forEach(tap -> trips.add(createIncompleteTrip(tap)));

        return trips.stream()
                .sorted(Comparator.comparing((Trip trip) -> trip.tapOn().dateTimeUtc())
                        .thenComparing(trip -> trip.tapOn().id()))
                .toList();
    }

    private void validateUniqueTapIds(List<Tap> taps) {
        Set<Long> tapIds = new HashSet<>();
        for (Tap tap : taps) {
            if (!tapIds.add(tap.id())) {
                throw new IllegalArgumentException("Duplicate tap ID: " + tap.id());
            }
        }
    }

    private void processTap(Tap tap, Map<TripKey, Tap> openTaps, List<Trip> trips) {
        TripKey tripKey = TripKey.from(tap);

        if (tap.type() == TapType.ON) {
            Tap previousTapOn = openTaps.put(tripKey, tap);
            if (previousTapOn != null) {
                trips.add(createIncompleteTrip(previousTapOn));
            }
            return;
        }

        Tap tapOn = openTaps.remove(tripKey);
        if (tapOn != null) {
            trips.add(createFinishedTrip(tapOn, tap));
        }
    }

    private Trip createFinishedTrip(Tap tapOn, Tap tapOff) {
        int chargeInCents = fareCalculator.chargeFor(tapOn.stopId(), tapOff.stopId());
        TripStatus status = chargeInCents == 0 ? TripStatus.CANCELLED : TripStatus.COMPLETED;
        return new Trip(tapOn, Optional.of(tapOff), chargeInCents, status);
    }

    private Trip createIncompleteTrip(Tap tapOn) {
        int chargeInCents = fareCalculator.maximumChargeFrom(tapOn.stopId());
        return new Trip(tapOn, Optional.empty(), chargeInCents, TripStatus.INCOMPLETE);
    }

    private record TripKey(String pan, String companyId, String busId) {
        private static TripKey from(Tap tap) {
            return new TripKey(tap.pan(), tap.companyId(), tap.busId());
        }
    }
}
