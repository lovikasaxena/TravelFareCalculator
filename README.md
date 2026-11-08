# Travel Fare Calculator

A small Java application that turns bus-card tap events into charged trips.

## Project layout

```
src/main/java/com/littlepay/trips/  Application source
src/test/java/com/littlepay/trips/  JUnit tests
```

The application uses the Java standard library. JUnit 5 is included only for automated tests.

## Build and test

Import the project as a Gradle project in IntelliJ. To run the current fare-calculator test harness from Gradle, use:

```
./gradlew check
```

The `check` task compiles the application and runs the JUnit test suite.

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
- Order generated trips by tap-on time, then by tap ID when times are equal.
- Parse and write date-times as `dd-MM-yyyy HH:mm:ss`. Treat input values as UTC, without time-zone conversion.

## Planned delivery

1. Domain models and fare calculation.
2. Tap matching and trip creation.
3. CSV input, output, and command-line application.
4. Test harness and the required example output.

Run instructions will be added with the command-line application.
