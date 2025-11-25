package com.github.therealguru.totemfletching.listener;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.service.EntTrailService;
import com.github.therealguru.totemfletching.service.ResearchPointService;
import com.github.therealguru.totemfletching.service.TotemService;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.*;
import net.runelite.api.gameval.VarPlayerID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PluginEventListener {

    private final Client client;
    private final TotemService totemService;
    private final EntTrailService entTrailService;
    private final ResearchPointService researchPointService;
    private final TotemFletchingConfig config;

    @Subscribe
    public void onVarbitChanged(final VarbitChanged varbitChanged) {
        if (varbitChanged.getVarbitId() == VarPlayerID.ENT_TOTEMS_RESEARCH_POINTS) {
            researchPointService.onVarbitChanged();
        }
        if (varbitChanged.getVarbitId() < VarbitID.ENT_TOTEMS_SITE_1_BASE
                || varbitChanged.getVarbitId() > VarbitID.ENT_TOTEMS_SITE_8_ALL_MULTIANIMALS)
            return;

        totemService.onVarbitChanged(varbitChanged);
    }

    @Subscribe
    public void onGameObjectSpawned(final GameObjectSpawned gameObjectSpawned) {
        totemService.addGameObject(gameObjectSpawned.getGameObject());
        entTrailService.addEntTrail(gameObjectSpawned);
    }

    @Subscribe
    public void onGameObjectDespawned(final GameObjectDespawned gameObjectDespawned) {
        totemService.removeGameObject(gameObjectDespawned.getGameObject());
        entTrailService.removeEntTrail(gameObjectDespawned);
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event) {
        if (event.getGameState().equals(GameState.LOADING)) {
            totemService.clearGameObjects();
            entTrailService.clearEntTrails();
        }
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged configChanged) {
        log.debug("Config has reloaded {}", config.notificationDecayTotem2());
        totemService.onConfigChange(config);
    }

    @Subscribe
    public void onGameTick(final GameTick gameTick) {
        totemService.updateClosestTotem(client.getLocalPlayer());
    }
}
