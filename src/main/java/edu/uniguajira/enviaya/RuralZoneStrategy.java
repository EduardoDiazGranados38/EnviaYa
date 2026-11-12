package edu.uniguajira.enviaya;

final class RuralZoneStrategy implements ZoneStrategy {
    private static final double RECARGO_RURAL = 0.10;

    @Override
    public double applySurcharge(double baseTariff) {
        return baseTariff + baseTariff * RECARGO_RURAL;
    }
}
