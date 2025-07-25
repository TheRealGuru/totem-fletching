package com.github.therealguru.totemfletching;

import com.github.therealguru.totemfletching.overlay.CarvingActionOverlay;
import com.github.therealguru.totemfletching.overlay.EntTrailOverlay;
import com.github.therealguru.totemfletching.overlay.TotemFletchingOverlay;
import com.github.therealguru.totemfletching.service.EntTrailService;
import com.github.therealguru.totemfletching.service.TotemService;
import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
        name = "Totem Fletching"
)
public class TotemFletchingPlugin extends Plugin {

    @Inject
    private Client client;
    @Inject
    private TotemFletchingConfig config;
    @Inject
    private OverlayManager overlayManager;

    private TotemService totemService;
    private EntTrailService entTrailService;
    private TotemFletchingOverlay gameOverlay;
    private CarvingActionOverlay carvingOverlay;
    private EntTrailOverlay entTrailOverlay;

    @Override
    protected void startUp() throws Exception {
        totemService = new TotemService();
        entTrailService = new EntTrailService();
        gameOverlay = new TotemFletchingOverlay(this, totemService, client);
        carvingOverlay = new CarvingActionOverlay(totemService, config, client);
        entTrailOverlay = new EntTrailOverlay(this, config, entTrailService, client);
        overlayManager.add(gameOverlay);
        overlayManager.add(carvingOverlay);
        overlayManager.add(entTrailOverlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(gameOverlay);
        overlayManager.remove(carvingOverlay);
        overlayManager.remove(entTrailOverlay);
    }

    @Provides
    TotemFletchingConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(TotemFletchingConfig.class);
    }

    @Subscribe
    public void onVarbitChanged(final VarbitChanged varbitChanged) {
        if (varbitChanged.getVarbitId() < 17611 || varbitChanged.getVarbitId() > 17754) return;

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
    public void onGameTick(final GameTick gameTick) {
        totemService.updateClosestTotem(client.getLocalPlayer());
    }
}
