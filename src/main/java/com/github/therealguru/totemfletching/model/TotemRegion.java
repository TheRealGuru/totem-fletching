package com.github.therealguru.totemfletching.model;

import net.runelite.api.coords.WorldPoint;

public class TotemRegion {
    public static boolean isInsidePolygon(int x, int y, int[][] polygon) {
        boolean inside = false;

        for (int i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
            int xi = polygon[i][0], yi = polygon[i][1];
            int xj = polygon[j][0], yj = polygon[j][1];

            boolean intersects =
                    ((yi > y) != (yj > y))
                            && (x < (double) (xj - xi) * (y - yi) / (double) (yj - yi) + xi);

            if (intersects) {
                inside = !inside;
            }
        }

        return inside;
    }

    public static boolean isInsideAuburnvale(WorldPoint point) {
        return isInsidePolygon(point.getX(), point.getY(), Constants.ABURNVALE_POLYGON);
    }
}
