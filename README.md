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

## Decisions awaiting confirmation

No implementation assumptions have been approved yet. The following questions must be resolved before tap matching is implemented:

- Should taps be matched by PAN alone, or by PAN together with company and bus?
- Should input taps be sorted by timestamp before matching?
- How should an unmatched tap-off be represented, if at all?
- How should a second tap-on be handled when an earlier tap-on for the same matching key is still open?
- Is `dd-MM-yyyy HH:mm:ss` the required UTC date-time format for all input and output files?

## Planned delivery

1. Domain models and fare calculation.
2. Tap matching and trip creation.
3. CSV input, output, and command-line application.
4. Test harness and the required example output.

Run instructions will be added with the command-line application.
