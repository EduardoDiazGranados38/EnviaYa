package edu.uniguajira.enviaya;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InsularZoneTest {
    @Test
    void cr01_insularZoneAddsSeventyPercentSurcharge() {
        assertEquals(15300, EnviosLegacy.calc(4, "insular", "normal", 1, null),
                "CR-01");
    }

    @Test
    void cr02_insularZoneIgnoresValidCoupon() {
        assertEquals(15300, EnviosLegacy.calc(4, "insular", "normal", 1, "ENVIO10"),
                "CR-02");
    }

    @Test
    void cr03_insularZoneKeepsCustomerDiscountButIgnoresCoupon() {
        assertEquals(14500, EnviosLegacy.calc(4, "insular", "frecuente", 1, "ENVIO10"),
                "CR-03");
    }

    @Test
    void cr04_insularWeekendSurchargeIsAppliedBeforeDiscounts() {
        assertEquals(17300, EnviosLegacy.calc(4, "insular", "normal", 6, "ENVIO10"),
                "CR-04");
    }
}
