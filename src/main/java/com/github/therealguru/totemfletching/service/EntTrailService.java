package com.github.therealguru.totemfletching.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.runelite.api.DynamicObject;
import net.runelite.api.GameObject;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;

public class EntTrailService {

    private static final List<Integer> ENT_TRAIL_GAME_OBJECT_IDS = List.of(57115, 57116);
    private static final List<Integer> ENT_TRAIL_INACTIVE_ANIMATION_IDS = List.of(12344, 12345);
    private static final List<Integer> ENT_TRAIL_ACTIVE_ANIMATION_IDS = List.of(12346);

    private final List<GameObject> entTrails = new ArrayList<>();

    public List<GameObject> getInactiveEntTrails() {
        return entTrails.stream()
                .filter(entTrail -> entTrail.getRenderable() instanceof DynamicObject)
                .filter(
                        entTrail ->
                                ((DynamicObject) entTrail.getRenderable()).getAnimation() != null)
                .filter(
                        entTrail ->
                                ENT_TRAIL_INACTIVE_ANIMATION_IDS.contains(
                                        ((DynamicObject) entTrail.getRenderable())
                                                .getAnimation()
                                                .getId()))
                .collect(Collectors.toList());
    }

    public void addEntTrail(GameObjectSpawned spawned) {
        if (!isEntTrail(spawned.getGameObject())) return;

        entTrails.add(spawned.getGameObject());
    }

    public void removeEntTrail(GameObjectDespawned despawned) {
        entTrails.remove(despawned.getGameObject());
    }

    private boolean isEntTrail(GameObject gameObject) {
        return ENT_TRAIL_GAME_OBJECT_IDS.contains(gameObject.getId());
    }

    public void clearEntTrails() {
        this.entTrails.clear();
    }
}
