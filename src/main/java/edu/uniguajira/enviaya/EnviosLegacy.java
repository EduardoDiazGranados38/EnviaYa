package edu.uniguajira.enviaya;

public final class EnviosLegacy {
    private static final double PMAX = 45.0;
    private static final double L1 = 3.0;
    private static final double L2 = 10.0;
    private static final double RECARGO_RURAL = 0.10;
    private static final double RECARGO_NACIONAL = 0.40;
    private static final double DESC_VIP = 0.10;
    private static final double RECARGO_FINDE = 2000.0;

    private EnviosLegacy() {
    }

    public static int calc(double p, String z, String c, int d, String cup, boolean flag) {
        if (p <= 0 || p > PMAX) {
            throw new IllegalArgumentException("peso inválido");
        }

        double t;
        if (p <= L1) {
            t = 6000;
        } else if (p <= L2) {
            t = 9000;
        } else {
            t = 9000 + (p - L2) * 800;
        }

        if ("urbana".equals(z)) {
            t = t;
        } else if ("rural".equals(z)) {
            t = t + t * RECARGO_RURAL;
        } else if ("nacional".equals(z)) {
            t = t + t * RECARGO_NACIONAL;
        } else {
            throw new IllegalArgumentException("zona inválida");
        }

        if (d < 1 || d > 7) {
            throw new IllegalArgumentException("día inválido");
        }

        if (d >= 6) {
            t = t + RECARGO_FINDE;
        }

        if ("frecuente".equals(c)) {
            t = t - t * 0.05;
        }
        if ("vip".equals(c)) {
            t = t - t * DESC_VIP;
        }
        if (cup != null) {
            if ("ENVIO10".equals(cup)) {
                t = t - t * 0.10;
            }
        }

        if (t < 5000) {
            t = 5000;
        }

        if (flag) {
            System.out.println("costo: " + t);
        }
        return (int) (Math.round(t / 100.0) * 100);
    }
}
