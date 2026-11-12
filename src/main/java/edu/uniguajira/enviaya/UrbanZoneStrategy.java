package edu.uniguajira.enviaya;

final class UrbanZoneStrategy implements ZoneStrategy {
    @Override
    public double applySurcharge(double baseTariff) {
        return baseTariff;
    }
}
