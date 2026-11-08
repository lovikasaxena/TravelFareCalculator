# LittlePay coding exercise

A small Java application that turns bus-card tap events into charged trips.

## Project layout

```
src/main/java/com/littlepay/trips/  Application source
src/test/java/com/littlepay/trips/  Dependency-free test harness
```

The project will use only the Java standard library. This keeps the solution easy to run and keeps the business rules visible in the code.

## Confirmed requirements from the brief

- The input CSV is well formed and has all required data.
- Fares apply in either direction.
- A missing tap-off produces an incomplete trip charged at the highest fare from the tap-on stop.
- A tap-on and tap-off at the same stop produces a cancelled trip with no charge.

## Assumptions / Implementation decisions

- Match taps using PAN, CompanyId, and BusID. This prevents taps from separate buses or companies being paired for the same card.
- Sort input by `DateTimeUTC`, then by ID when timestamps are equal.
- Ignore an unmatched tap-off because it has no corresponding start or fare basis.
- When a second tap-on arrives for an active matching key, create an `INCOMPLETE` trip from the earlier tap-on and start a new trip from the later tap-on.
- Parse and write date-times as `dd-MM-yyyy HH:mm:ss`. Treat input values as UTC, without time-zone conversion.

## Planned delivery

1. Domain models and fare calculation.
2. Tap matching and trip creation.
3. CSV input, output, and command-line application.
4. Test harness and the required example output.

Run instructions will be added with the command-line application.
