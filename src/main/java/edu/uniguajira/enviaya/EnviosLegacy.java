package edu.uniguajira.enviaya;

public final class EnviosLegacy {
    private static final double PMAX = 45.0;
    private static final double L1 = 3.0;
    private static final double L2 = 10.0;
    private static final int DIA_MINIMO = 1;
    private static final int DIA_MAXIMO = 7;
    private static final int INICIO_FIN_DE_SEMANA = 6;
    private static final double TARIFA_TRAMO_UNO = 6000.0;
    private static final double TARIFA_TRAMO_DOS = 9000.0;
    private static final double TARIFA_POR_KILO_ADICIONAL = 800.0;
    private static final double RECARGO_FINDE = 2000.0;
    private static final double DESC_FRECUENTE = 0.05;
    private static final double DESC_VIP = 0.10;
    private static final double DESC_CUPON = 0.10;
    private static final double TARIFA_MINIMA = 5000.0;
    private static final double INCREMENTO_REDONDEO = 100.0;

    private EnviosLegacy() {
    }

    public static int calc(double weight, String zone, String customer, int day, String coupon) {
        validateWeight(weight);
        ZoneStrategy zoneStrategy = ZoneStrategyFactory.forZone(zone);
        validateDay(day);

        double total = baseTariff(weight);
        total = zoneStrategy.applySurcharge(total);
        total = applyWeekendSurcharge(total, day);
        total = applyCustomerDiscount(total, customer);
        total = applyCouponDiscount(total, coupon, zoneStrategy.acceptsCoupon());
        total = Math.max(total, TARIFA_MINIMA);

        return roundToHundred(total);
    }

    private static void validateWeight(double weight) {
        if (weight <= 0 || weight > PMAX) {
            throw new IllegalArgumentException("peso inválido");
        }
    }

    private static void validateDay(int day) {
        if (day < DIA_MINIMO || day > DIA_MAXIMO) {
            throw new IllegalArgumentException("día inválido");
        }
    }

    private static double baseTariff(double weight) {
        if (weight <= L1) {
            return TARIFA_TRAMO_UNO;
        }
        if (weight <= L2) {
            return TARIFA_TRAMO_DOS;
        }
        return TARIFA_TRAMO_DOS + (weight - L2) * TARIFA_POR_KILO_ADICIONAL;
    }

    private static double applyWeekendSurcharge(double total, int day) {
        return day >= INICIO_FIN_DE_SEMANA ? total + RECARGO_FINDE : total;
    }

    private static double applyCustomerDiscount(double total, String customer) {
        if ("frecuente".equals(customer)) {
            total = total - total * DESC_FRECUENTE;
        }
        if ("vip".equals(customer)) {
            total = total - total * DESC_VIP;
        }
        return total;
    }

    private static double applyCouponDiscount(double total, String coupon, boolean acceptsCoupon) {
        if (acceptsCoupon && "ENVIO10".equals(coupon)) {
            return total - total * DESC_CUPON;
        }
        return total;
    }

    private static int roundToHundred(double total) {
        return (int) (Math.round(total / INCREMENTO_REDONDEO) * INCREMENTO_REDONDEO);
    }
}
