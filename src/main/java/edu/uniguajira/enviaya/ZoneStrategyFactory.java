package edu.uniguajira.enviaya;

final class ZoneStrategyFactory {
    private static final ZoneStrategy URBAN = new UrbanZoneStrategy();
    private static final ZoneStrategy RURAL = new RuralZoneStrategy();
    private static final ZoneStrategy NATIONAL = new NationalZoneStrategy();

    private ZoneStrategyFactory() {
    }

    static ZoneStrategy forZone(String zone) {
        if ("urbana".equals(zone)) {
            return URBAN;
        }
        if ("rural".equals(zone)) {
            return RURAL;
        }
        if ("nacional".equals(zone)) {
            return NATIONAL;
        }
        throw new IllegalArgumentException("zona inválida");
    }
}
