package edu.uniguajira.enviaya;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ZoneStrategyTest {
    @Test
    void urbanZoneDoesNotAddSurcharge() {
        assertEquals(9000.0, ZoneStrategyFactory.forZone("urbana").applySurcharge(9000.0));
    }

    @Test
    void ruralZoneAddsTenPercentSurcharge() {
        assertEquals(9900.0, ZoneStrategyFactory.forZone("rural").applySurcharge(9000.0));
    }

    @Test
    void nationalZoneAddsFortyPercentSurcharge() {
        assertEquals(12600.0, ZoneStrategyFactory.forZone("nacional").applySurcharge(9000.0));
    }

    @Test
    void unknownZoneIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> ZoneStrategyFactory.forZone("periurbana"));
    }

    @Test
    void nullZoneIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> ZoneStrategyFactory.forZone(null));
    }
}
