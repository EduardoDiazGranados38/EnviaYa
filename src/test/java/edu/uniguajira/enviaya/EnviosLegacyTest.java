package edu.uniguajira.enviaya;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class EnviosLegacyTest {
    @TestFactory
    Stream<DynamicTest> blackBoxCases() {
        return cases().stream().map(testCase ->
                DynamicTest.dynamicTest(testCase.id + " - " + testCase.title, () -> {
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
                }));
    }

    @Test
    void whiteBoxWeightGuardChecksUpperLegacyLimit() {
        assertThrows(IllegalArgumentException.class,
                () -> EnviosLegacy.calc(51, "urbana", "normal", 1, null, false),
                "WB-15");
    }

    @Test
    void whiteBoxFlagBranchPrintsCost() {
        assertEquals(9000, EnviosLegacy.calc(4, "urbana", "normal", 1, null, true),
                "WB-14");
    }

    private static int calculate(TestCase testCase) {
        return EnviosLegacy.calc(
                Double.parseDouble(testCase.weight),
                testCase.zone,
                testCase.customer,
                testCase.day,
                testCase.coupon,
                false);
    }

    private static List<TestCase> cases() {
        List<TestCase> cases = new ArrayList<>();
        cases.add(error("CP-01", "zero weight", "0", "urbana", "normal", 1, null,
                "peso inválido"));
        cases.add(error("CP-02", "negative weight", "-1", "urbana", "normal", 1, null,
                "peso inválido"));
        cases.add(cost("CP-03", "maximum valid weight", "45", "urbana", "normal", 1, null, 37000));
        cases.add(error("CP-04", "weight above PMAX", "45.001", "urbana", "normal", 1, null,
                "peso inválido"));
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
    }
}
