package com.github.therealguru.totemfletching.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.GameObject;

@Getter
@Setter
public class Totem {

    private static final int MAXIMUM_POINTS = 15000;

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

    public int getPoints() {
        return points;
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

    public boolean isRenderable(Client client) {
        return totemGameObject != null
                && pointsGameObject != null
                && TotemRegion.isInsideAuburnvale(client.getLocalPlayer().getWorldLocation());
    }

    public boolean isPointCapped() {
        return points >= MAXIMUM_POINTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Totem totem = (Totem) o;
        return totemId == totem.totemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totemId);
    }
}
