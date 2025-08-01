package com.github.therealguru.totemfletching.service;

import java.util.*;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.DynamicObject;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.gameval.NpcID;

@Slf4j
@Singleton
public class EntTrailService {

    private static final int MIN_ENT_ID = NpcID.ENT_TOTEMS_ENT_DESTINATION_1;
    private static final int MAX_ENT_ID = NpcID.ENT_TOTEMS_ENT_DESTINATION_8;
    private static final List<Integer> ENT_TRAIL_GAME_OBJECT_IDS = List.of(57115, 57116);
    private static final List<Integer> ENT_TRAIL_INACTIVE_ANIMATION_IDS = List.of(12344, 12345);
    private static final List<Integer> ENT_TRAIL_ACTIVE_ANIMATION_IDS = List.of(12346);

    private final Map<Integer, Set<WorldPoint>> entLocations = new HashMap<>();
    private final Set<WorldPoint> entTrailsSeen = new HashSet<>();
    private final List<GameObject> entTrails = new ArrayList<>();
    private final List<NPC> entNpcs = new ArrayList<>();

    public EntTrailService() {}

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
        if (entTrailsSeen.add(spawned.getTile().getWorldLocation())) {
            log.debug(
                    "A new ent trail location has been detected updated list is now {}",
                    entTrailsSeen);
        }
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

    public void addEntNpc(NPC npc) {
        int id = npc.getId();
        if (id < MIN_ENT_ID || id > MAX_ENT_ID) return;

        entNpcs.add(npc);
        registerEntLocation(npc);
    }

    public void removeEntNpc(NPC npc) {
        entNpcs.remove(npc);
    }

    public void clearEntNpcs() {
        this.entNpcs.clear();
    }

    public void updateEntNpcLocations() {
        for (NPC npc : this.entNpcs) {
            registerEntLocation(npc);
        }
    }

    private void registerEntLocation(NPC npc) {
        int totemId = getTotemIdForEnt(npc.getId());
        Set<WorldPoint> locations = this.entLocations.getOrDefault(totemId, new HashSet<>());
        if (locations.add(npc.getWorldLocation())) {
            log.debug(
                    "Updated location list for totem id {} the total tiles we have seen the ent on are now {}",
                    totemId,
                    locations);
        }

        this.entLocations.put(totemId, locations);
    }

    private int getTotemIdForEnt(int npcId) {
        return npcId - MIN_ENT_ID + 1;
    }
}
