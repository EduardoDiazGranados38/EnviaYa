package edu.uniguajira.enviaya;

final class UrbanZoneStrategy implements ZoneStrategy {
    @Override
    public double applySurcharge(double baseTariff) {
        return baseTariff;
    }

    @Override
    public boolean acceptsCoupon() {
        return true;
    }
}
