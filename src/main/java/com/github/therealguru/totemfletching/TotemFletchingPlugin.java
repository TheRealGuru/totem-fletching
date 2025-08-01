package com.github.therealguru.totemfletching;

import com.github.therealguru.totemfletching.service.EntTrailService;
import com.github.therealguru.totemfletching.service.OverlayService;
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

@Slf4j
@PluginDescriptor(name = "Totem Fletching")
public class TotemFletchingPlugin extends Plugin {

    @Inject private Client client;

    @Inject private OverlayService overlayService;
    @Inject private TotemService totemService;
    @Inject private EntTrailService entTrailService;

    @Override
    protected void startUp() throws Exception {
        overlayService.registerOverlays();
    }

    @Override
    protected void shutDown() throws Exception {
        overlayService.unregisterOverlays();
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
    public void onNpcSpawned(final NpcSpawned npcSpawned) {
        entTrailService.addEntNpc(npcSpawned.getNpc());
    }

    @Subscribe
    public void onNpcDespawned(final NpcDespawned npcDespawned) {
        entTrailService.removeEntNpc(npcDespawned.getNpc());
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event) {
        if (event.getGameState().equals(GameState.LOADING)) {
            totemService.clearGameObjects();
            entTrailService.clearEntTrails();
            entTrailService.clearEntNpcs();
        }
    }

    @Subscribe
    public void onGameTick(final GameTick gameTick) {
        totemService.updateClosestTotem(client.getLocalPlayer());
        entTrailService.updateEntNpcLocations();
    }
}
