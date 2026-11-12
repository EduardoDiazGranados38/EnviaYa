package edu.uniguajira.enviaya;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class EnviosLegacyTest {
    @Test void cp01_zeroWeight() { verify("CP-01"); }
    @Test void cp02_negativeWeight() { verify("CP-02"); }
    @Test void cp03_maximumValidWeight() { verify("CP-03"); }
    @Test void cp04_weightAboveMaximum() { verify("CP-04"); }
    @Test void cp05_invalidZone() { verify("CP-05"); }
    @Test void cp06_dayBelowRange() { verify("CP-06"); }
    @Test void cp07_dayAboveRange() { verify("CP-07"); }
    @Test void cp08_weightAtL1() { verify("CP-08"); }
    @Test void cp09_weightAboveL1() { verify("CP-09"); }
    @Test void cp10_weightAtL2() { verify("CP-10"); }
    @Test void cp11_proportionalTariff() { verify("CP-11"); }
    @Test void cp12_urbanZone() { verify("CP-12"); }
    @Test void cp13_ruralSurcharge() { verify("CP-13"); }
    @Test void cp14_nationalSurcharge() { verify("CP-14"); }
    @Test void cp15_saturdaySurcharge() { verify("CP-15"); }
    @Test void cp16_sundayAndRuralSurcharge() { verify("CP-16"); }
    @Test void cp17_weekdayWithoutWeekendSurcharge() { verify("CP-17"); }
    @Test void cp18_frequentCustomerDiscount() { verify("CP-18"); }
    @Test void cp19_vipDiscount() { verify("CP-19"); }
    @Test void cp20_validCoupon() { verify("CP-20"); }
    @Test void cp21_customerAndCouponDiscounts() { verify("CP-21"); }
    @Test void cp22_unknownCouponIgnored() { verify("CP-22"); }
    @Test void cp23_minimumAfterDiscounts() { verify("CP-23"); }
    @Test void cp24_roundingTieGoesUp() { verify("CP-24"); }
    @Test void cp25_smallestPositiveWeight() { verify("CP-25"); }
    @Test void cp26_justAboveL2() { verify("CP-26"); }
    @Test void cp27_justBelowMaximum() { verify("CP-27"); }
    @Test void cp28_weekendBeforeCustomerDiscount() { verify("CP-28"); }
    @Test void cp29_normalWeekendNoCoupon() { verify("CP-29"); }
    @Test void cp30_normalWeekendValidCoupon() { verify("CP-30"); }
    @Test void cp31_normalWeekendUnknownCoupon() { verify("CP-31"); }
    @Test void cp32_frequentWeekendNoCoupon() { verify("CP-32"); }
    @Test void cp33_frequentWeekendValidCoupon() { verify("CP-33"); }
    @Test void cp34_frequentUnknownCoupon() { verify("CP-34"); }
    @Test void cp35_frequentWeekendUnknownCoupon() { verify("CP-35"); }
    @Test void cp36_vipWeekendNoCoupon() { verify("CP-36"); }
    @Test void cp37_vipValidCoupon() { verify("CP-37"); }
    @Test void cp38_vipWeekendValidCoupon() { verify("CP-38"); }
    @Test void cp39_vipUnknownCoupon() { verify("CP-39"); }
    @Test void cp40_vipWeekendUnknownCoupon() { verify("CP-40"); }
    @Test void cp41_justBelowL1() { verify("CP-41"); }
    @Test void cp42_justBelowL2() { verify("CP-42"); }

    private static void verify(String caseId) {
        TestCase testCase = findCase(caseId);
        if (testCase.expectedMessage != null) {
            IllegalArgumentException error = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculate(testCase), testCase.id);
            assertEquals(testCase.expectedMessage, error.getMessage(),
                    testCase.id + " error message");
        } else if (testCase.expectsError) {
            assertThrows(IllegalArgumentException.class,
                    () -> calculate(testCase), testCase.id);
        } else {
            assertEquals(testCase.expectedCost, calculate(testCase),
                    testCase.id + " expected cost");
        }
    }

    private static TestCase findCase(String id) {
        for (TestCase testCase : cases()) {
            if (testCase.id.equals(id)) {
                return testCase;
            }
        }
        throw new IllegalArgumentException("Unknown test case: " + id);
    }

    @Test
    void whiteBoxWeightGuardChecksUpperLegacyLimit() {
        assertThrows(IllegalArgumentException.class,
                () -> EnviosLegacy.calc(51, "urbana", "normal", 1, null),
                "WB-15");
    }

    private static int calculate(TestCase testCase) {
        return EnviosLegacy.calc(
                Double.parseDouble(testCase.weight),
                testCase.zone,
                testCase.customer,
                testCase.day,
                testCase.coupon);
    }

    private static List<TestCase> cases() {
        List<TestCase> cases = new ArrayList<>();
        cases.add(error("CP-01", "zero weight", "0", "urbana", "normal", 1, null,
                "peso inválido"));
        cases.add(error("CP-02", "negative weight", "-1", "urbana", "normal", 1, null,
                "peso inválido"));
        cases.add(cost("CP-03", "maximum valid weight", "45", "urbana", "normal", 1, null, 37000));
        cases.add(error("CP-04", "weight above PMAX", "45.001", "urbana", "normal", 1, null,
                null));
        cases.add(error("CP-05", "invalid zone", "4", "periurbana", "normal", 1, null, null));
        cases.add(error("CP-06", "day below range", "4", "urbana", "normal", 0, null, null));
        cases.add(error("CP-07", "day above range", "4", "urbana", "normal", 8, null, null));
        cases.add(cost("CP-08", "weight at L1", "3", "urbana", "normal", 1, null, 6000));
        cases.add(cost("CP-09", "weight above L1", "3.001", "urbana", "normal", 1, null, 9000));
        cases.add(cost("CP-10", "weight at L2", "10", "urbana", "normal", 1, null, 9000));
        cases.add(cost("CP-11", "proportional tariff above L2", "10.125", "urbana", "normal", 1, null, 9100));
        cases.add(cost("CP-12", "urban zone", "4", "urbana", "normal", 1, null, 9000));
        cases.add(cost("CP-13", "rural surcharge", "4", "rural", "normal", 1, null, 9900));
        cases.add(cost("CP-14", "national surcharge", "4", "nacional", "normal", 1, null, 12600));
        cases.add(cost("CP-15", "Saturday surcharge", "4", "urbana", "normal", 6, null, 11000));
        cases.add(cost("CP-16", "Sunday and rural surcharge", "4", "rural", "normal", 7, null, 11900));
        cases.add(cost("CP-17", "weekday without weekend surcharge", "4", "rural", "normal", 5, null, 9900));
        cases.add(cost("CP-18", "frequent customer discount", "4", "urbana", "frecuente", 1, null, 8600));
        cases.add(cost("CP-19", "VIP discount", "4", "urbana", "vip", 1, null, 8100));
        cases.add(cost("CP-20", "valid coupon", "4", "urbana", "normal", 1, "ENVIO10", 8100));
        cases.add(cost("CP-21", "frequent customer and coupon", "4", "urbana", "frecuente", 1, "ENVIO10", 7700));
        cases.add(cost("CP-22", "unknown coupon is ignored", "4", "urbana", "normal", 1, "OTRO", 9000));
        cases.add(cost("CP-23", "minimum charge after discounts", "3", "urbana", "vip", 1, "ENVIO10", 5000));
        cases.add(cost("CP-24", "rounding tie goes up", "11.4375", "urbana", "normal", 1, null, 10200));
        cases.add(cost("CP-25", "smallest positive partition", "0.001", "urbana", "normal", 1, null, 6000));
        cases.add(cost("CP-26", "just above L2", "10.001", "urbana", "normal", 1, null, 9000));
        cases.add(cost("CP-27", "just below PMAX", "44.999", "urbana", "normal", 1, null, 37000));
        cases.add(cost("CP-28", "weekend before frequent discount", "4", "rural", "frecuente", 6, null, 11300));
        cases.add(cost("CP-29", "normal weekend without coupon", "4", "urbana", "normal", 6, null, 11000));
        cases.add(cost("CP-30", "normal weekend with valid coupon", "4", "urbana", "normal", 6, "ENVIO10", 9900));
        cases.add(cost("CP-31", "normal weekend with unknown coupon", "4", "urbana", "normal", 6, "OTRO", 11000));
        cases.add(cost("CP-32", "frequent weekend without coupon", "4", "urbana", "frecuente", 6, null, 10500));
        cases.add(cost("CP-33", "frequent weekend with valid coupon", "4", "urbana", "frecuente", 6, "ENVIO10", 9400));
        cases.add(cost("CP-34", "frequent customer with unknown coupon", "4", "urbana", "frecuente", 1, "OTRO", 8600));
        cases.add(cost("CP-35", "frequent weekend with unknown coupon", "4", "urbana", "frecuente", 6, "OTRO", 10500));
        cases.add(cost("CP-36", "VIP weekend without coupon", "4", "urbana", "vip", 6, null, 9900));
        cases.add(cost("CP-37", "VIP with valid coupon", "4", "urbana", "vip", 1, "ENVIO10", 7300));
        cases.add(cost("CP-38", "VIP weekend with valid coupon", "4", "urbana", "vip", 6, "ENVIO10", 8900));
        cases.add(cost("CP-39", "VIP with unknown coupon", "4", "urbana", "vip", 1, "OTRO", 8100));
        cases.add(cost("CP-40", "VIP weekend with unknown coupon", "4", "urbana", "vip", 6, "OTRO", 9900));
        cases.add(cost("CP-41", "just below L1", "2.999", "urbana", "normal", 1, null, 6000));
        cases.add(cost("CP-42", "just below L2", "9.999", "urbana", "normal", 1, null, 9000));
        return cases;
    }

    private static TestCase cost(String id, String title, String weight, String zone,
            String customer, int day, String coupon, int expectedCost) {
        return new TestCase(id, title, weight, zone, customer, day, coupon, expectedCost, false, null);
    }

    private static TestCase error(String id, String title, String weight, String zone,
            String customer, int day, String coupon, String expectedMessage) {
        return new TestCase(id, title, weight, zone, customer, day, coupon, 0, true, expectedMessage);
    }

    private static final class TestCase {
        private final String id;
        private final String title;
        private final String weight;
        private final String zone;
        private final String customer;
        private final int day;
        private final String coupon;
        private final int expectedCost;
        private final boolean expectsError;
        private final String expectedMessage;

        private TestCase(String id, String title, String weight, String zone, String customer,
                int day, String coupon, int expectedCost, boolean expectsError, String expectedMessage) {
            this.id = id;
            this.title = title;
            this.weight = weight;
            this.zone = zone;
            this.customer = customer;
            this.day = day;
            this.coupon = coupon;
            this.expectedCost = expectedCost;
            this.expectsError = expectsError;
            this.expectedMessage = expectedMessage;
        }

        @Override
        public String toString() {
            return id + " - " + title;
        }
    }
}
