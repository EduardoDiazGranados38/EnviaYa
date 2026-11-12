package edu.uniguajira.enviaya;

public final class EnviosLegacy {
    private static final double PMAX = 45.0;
    private static final double L1 = 3.0;
    private static final double L2 = 10.0;
    private static final double RECARGO_RURAL = 0.10;
    private static final double RECARGO_NACIONAL = 0.40;
    private static final double DESC_VIP = 0.10;
    private static final double RECARGO_FINDE = 2000.0;
    private static final double TARIFA_MINIMA = 5000.0;
    private static final double INCREMENTO_REDONDEO = 100.0;

    private EnviosLegacy() {
    }

    public static int calc(double p, String z, String c, int d, String cup) {
        validateWeight(p);
        validateZone(z);
        validateDay(d);

        double total = baseTariff(p);
        total = applyZoneSurcharge(total, z);
        total = applyWeekendSurcharge(total, d);
        total = applyCustomerDiscount(total, c);
        total = applyCouponDiscount(total, cup);
        total = Math.max(total, TARIFA_MINIMA);

        return roundToHundred(total);
    }

    private static void validateWeight(double weight) {
        if (weight <= 0 || weight > PMAX) {
            throw new IllegalArgumentException("peso inválido");
        }
    }

    private static void validateZone(String zone) {
        if (!"urbana".equals(zone) && !"rural".equals(zone) && !"nacional".equals(zone)) {
            throw new IllegalArgumentException("zona inválida");
        }
    }

    private static void validateDay(int day) {
        if (day < 1 || day > 7) {
            throw new IllegalArgumentException("día inválido");
        }
    }

    private static double baseTariff(double weight) {
        if (weight <= L1) {
            return 6000;
        }
        if (weight <= L2) {
            return 9000;
        }
        return 9000 + (weight - L2) * 800;
    }

    private static double applyZoneSurcharge(double total, String zone) {
        if ("rural".equals(zone)) {
            return total + total * RECARGO_RURAL;
        }
        if ("nacional".equals(zone)) {
            return total + total * RECARGO_NACIONAL;
        }
        return total;
    }

    private static double applyWeekendSurcharge(double total, int day) {
        return day >= 6 ? total + RECARGO_FINDE : total;
    }

    private static double applyCustomerDiscount(double total, String customer) {
        if ("frecuente".equals(customer)) {
            total = total - total * 0.05;
        }
        if ("vip".equals(customer)) {
            total = total - total * DESC_VIP;
        }
        return total;
    }

    private static double applyCouponDiscount(double total, String coupon) {
        if ("ENVIO10".equals(coupon)) {
            return total - total * 0.10;
        }
        return total;
    }

    private static int roundToHundred(double total) {
        return (int) (Math.round(total / INCREMENTO_REDONDEO) * INCREMENTO_REDONDEO);
    }
}
