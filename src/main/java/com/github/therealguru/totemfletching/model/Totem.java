package com.github.therealguru.totemfletching.model;

import lombok.Data;
import net.runelite.api.GameObject;

import java.util.HashMap;
import java.util.Map;


@Data
public class Totem {
    private int totemId;
    private int totemGameObjectId;
    private int pointsGameObjectId;
    private int startingVarbit;
    private GameObject totemGameObject;
    private GameObject pointsGameObject;
    private boolean carved = false;
    private int decoration = 0;
    private int decay = 0;
    private int base = 0;
    private int[] animals = new int[3];
    private Map<TotemTier, Integer> progress = new HashMap<>();
    private int points = 0;

    public Totem(int totemId, int totemGameObjectId, int pointsGameObjectId, int startingVarbit) {
        this.totemId = totemId;
        this.totemGameObjectId = totemGameObjectId;
        this.pointsGameObjectId = pointsGameObjectId;
        this.startingVarbit = startingVarbit;
        this.progress.put(TotemTier.LOW, 0);
        this.progress.put(TotemTier.MID, 0);
        this.progress.put(TotemTier.TOP, 0);
    }

    public boolean hasTotemStarted() {
        return base != 0;
    }

    public boolean isBuildingTotem() {
        return base >= 1 && base <= 6;
    }

    public boolean isDecorated() {
        return decoration == 4;
    }

    private int getValue(TotemTier totemTier) {
        return progress.getOrDefault(totemTier, 0);
    }

    public boolean isBottomComplete() {
        return getValue(TotemTier.LOW) > 4;
    }

    public boolean isMiddleComplete() {
        return getValue(TotemTier.MID) > 4;
    }

    public boolean isTopComplete() {
        return getValue(TotemTier.TOP) > 4;
    }

    public boolean isVarbitRelated(int varbit) {
        return varbit >= startingVarbit && varbit <= (startingVarbit + 17);
    }

    public boolean isRenderable() {
        return totemGameObject != null && pointsGameObject != null;
    }

}
