package edu.uniguajira.enviaya;

final class NationalZoneStrategy implements ZoneStrategy {
    private static final double RECARGO_NACIONAL = 0.40;

    @Override
    public double applySurcharge(double baseTariff) {
        return baseTariff + baseTariff * RECARGO_NACIONAL;
    }
}
