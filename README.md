# LittlePay coding exercise

A small Java application that turns bus-card tap events into charged trips.

## Project layout

```
src/main/java/com/littlepay/trips/  Application source
src/test/java/com/littlepay/trips/  Dependency-free test harness
```

The project will use only the Java standard library. This keeps the solution easy to run and keeps the business rules visible in the code.

## Assumptions

- The input CSV has the columns and valid values described in the exercise.
- Taps are processed in timestamp order, even if the input lines are not already ordered.
- **Pending confirmation:** a trip may be matched by PAN, company, and bus. The exercise does not specify the matching key, so this behaviour will not be implemented until it is confirmed.
- There is no more than one active tap-on for a PAN, company, and bus at a time.
- An unmatched tap-on creates an `INCOMPLETE` trip. An unmatched tap-off is ignored because it has no start from which to create a trip.
- Fares apply in either direction. Money is stored in cents and formatted as dollars only when writing the output CSV.
- The supplied date-time values represent UTC and use `dd-MM-yyyy HH:mm:ss`.

## Planned delivery

1. Domain models and fare calculation.
2. Tap matching and trip creation.
3. CSV input, output, and command-line application.
4. Test harness and the required example output.

Run instructions will be added with the command-line application.
