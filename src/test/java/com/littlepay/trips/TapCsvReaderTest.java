package com.littlepay.trips;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TapCsvReaderTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void readsTapsFromCsv() throws Exception {
        Path inputFile = temporaryDirectory.resolve("taps.csv");
        Files.writeString(inputFile, String.join("\n",
                "ID,DateTimeUTC,TapType,StopId,CompanyId,BusID,PAN",
                "1,22-01-2023 13:00:00,ON,Stop1,Company1,Bus37,TestPan1"));

        List<Tap> taps = new TapCsvReader().read(inputFile);

        assertEquals(List.of(new Tap(
                1,
                LocalDateTime.of(2023, 1, 22, 13, 0),
                TapType.ON,
                "Stop1",
                "Company1",
                "Bus37",
                "TestPan1")), taps);
    }
}
