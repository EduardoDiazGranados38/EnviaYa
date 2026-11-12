package edu.uniguajira.enviaya;

final class InsularZoneStrategy implements ZoneStrategy {
    private static final double RECARGO_INSULAR = 0.70;

    @Override
    public double applySurcharge(double baseTariff) {
        return baseTariff + baseTariff * RECARGO_INSULAR;
    }

    @Override
    public boolean acceptsCoupon() {
        return false;
    }
}
