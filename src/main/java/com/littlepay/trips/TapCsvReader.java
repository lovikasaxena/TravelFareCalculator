package com.littlepay.trips;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class TapCsvReader {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public List<Tap> read(Path inputFile) throws IOException {
        try (var lines = Files.lines(inputFile)) {
            return lines.skip(1)
                    .filter(line -> !line.isBlank())
                    .map(this::parseTap)
                    .toList();
        }
    }

    private Tap parseTap(String line) {
        String[] values = line.split(",", -1);
        return new Tap(
                Long.parseLong(values[0].trim()),
                LocalDateTime.parse(values[1].trim(), DATE_TIME_FORMAT),
                TapType.valueOf(values[2].trim()),
                values[3].trim(),
                values[4].trim(),
                values[5].trim(),
                values[6].trim());
    }
}
