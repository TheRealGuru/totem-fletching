package com.github.therealguru.totemfletching.service;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.DynamicObject;
import net.runelite.api.GameObject;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.gameval.AnimationID;
import net.runelite.api.gameval.ObjectID;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class EntTrailService {
    private static final List<Integer> ENT_TRAIL_GAME_OBJECT_IDS = ImmutableList.of(
        ObjectID.ENT_TOTEMS_TRAIL_PART_0,
        ObjectID.ENT_TOTEMS_TRAIL_PART_1
    );
    private static final List<Integer> ENT_TRAIL_INACTIVE_ANIMATION_IDS = ImmutableList.of(
        AnimationID.NATURE_ENT01_TRAIL01_INACTIVE_IDLE,
        AnimationID.NATURE_ENT01_TRAIL01_INACTIVE_SPAWN_TO_IDLE
    );
    private static final List<Integer> ENT_TRAIL_ACTIVE_ANIMATION_IDS = ImmutableList.of(
        AnimationID.NATURE_ENT01_TRAIL01_ACTIVE_IDLE,
        AnimationID.NATURE_ENT01_TRAIL01_ACTIVE_DESPAWN
    );

    private final List<GameObject> entTrails = new ArrayList<>();

    @Inject
    public EntTrailService() {
    }

    public List<GameObject> getInactiveEntTrails() {
        return entTrails.stream().filter(entTrail -> entTrail.getRenderable() instanceof DynamicObject)
            .filter(entTrail -> ((DynamicObject) entTrail.getRenderable()).getAnimation() != null)
            .filter(entTrail -> ENT_TRAIL_INACTIVE_ANIMATION_IDS.contains(((DynamicObject) entTrail.getRenderable()).getAnimation().getId()))
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
