package com.github.therealguru.totemfletching.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum TotemRegions {
    NORTH(Constants.Regions.NORTH),
    NORTH_EAST(Constants.Regions.NORTH_EAST),
    NORTH_WEST(Constants.Regions.NORTH_WEST),
    SOUTH(Constants.Regions.SOUTH),
    SOUTH_WEST(Constants.Regions.SOUTH_WEST),
    SOUTH_EAST(Constants.Regions.SOUTH_EAST);

    private final int id;

    TotemRegions(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final Set<Integer> VALID_REGION_IDS =
            Arrays.stream(values()).map(TotemRegions::getId).collect(Collectors.toSet());

    public static boolean isValid(int id) {
        return VALID_REGION_IDS.contains(id);
    }
}
