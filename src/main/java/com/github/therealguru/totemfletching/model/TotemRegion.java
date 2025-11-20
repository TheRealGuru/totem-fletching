package com.github.therealguru.totemfletching.model;

import net.runelite.api.coords.WorldPoint;

import java.awt.Polygon;

public class TotemRegion {
    public static final Polygon AUBURNVALE_POLYGON = new Polygon();
    // Slightly modified Wiki polyline of Auburn Valley
    static {
        AUBURNVALE_POLYGON.addPoint(1344, 3297);
        AUBURNVALE_POLYGON.addPoint(1333, 3312);
        AUBURNVALE_POLYGON.addPoint(1333, 3322);
        AUBURNVALE_POLYGON.addPoint(1325, 3330);
        AUBURNVALE_POLYGON.addPoint(1325, 3333);
        AUBURNVALE_POLYGON.addPoint(1330, 3338);
        AUBURNVALE_POLYGON.addPoint(1330, 3348);
        AUBURNVALE_POLYGON.addPoint(1340, 3348);
        AUBURNVALE_POLYGON.addPoint(1348, 3356);
        AUBURNVALE_POLYGON.addPoint(1348, 3380);
        AUBURNVALE_POLYGON.addPoint(1380, 3400);
        AUBURNVALE_POLYGON.addPoint(1400, 3400);
        AUBURNVALE_POLYGON.addPoint(1418, 3394);
        AUBURNVALE_POLYGON.addPoint(1480, 3364);
        AUBURNVALE_POLYGON.addPoint(1504, 3364);
        AUBURNVALE_POLYGON.addPoint(1504, 3312);
        AUBURNVALE_POLYGON.addPoint(1484, 3312);
        AUBURNVALE_POLYGON.addPoint(1468, 3304);
        AUBURNVALE_POLYGON.addPoint(1460, 3296);
        AUBURNVALE_POLYGON.addPoint(1460, 3292);
        AUBURNVALE_POLYGON.addPoint(1448, 3286);
        AUBURNVALE_POLYGON.addPoint(1421, 3286);
        AUBURNVALE_POLYGON.addPoint(1421, 3275);
        AUBURNVALE_POLYGON.addPoint(1411, 3270);
        AUBURNVALE_POLYGON.addPoint(1402, 3270);
        AUBURNVALE_POLYGON.addPoint(1402, 3268);
        AUBURNVALE_POLYGON.addPoint(1401, 3267);
        AUBURNVALE_POLYGON.addPoint(1401, 3266);
        AUBURNVALE_POLYGON.addPoint(1400, 3266);
        AUBURNVALE_POLYGON.addPoint(1400, 3263);
        AUBURNVALE_POLYGON.addPoint(1399, 3262);
        AUBURNVALE_POLYGON.addPoint(1399, 3259);
        AUBURNVALE_POLYGON.addPoint(1365, 3259);
    }

    public static boolean isInsideAuburnvale(WorldPoint point) {
        return AUBURNVALE_POLYGON.contains(point.getX(), point.getY());
    }
}
