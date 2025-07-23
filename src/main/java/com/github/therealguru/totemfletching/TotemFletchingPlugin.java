package com.github.therealguru.totemfletching;

import com.github.therealguru.totemfletching.overlay.TotemFletchingOverlay;
import com.github.therealguru.totemfletching.service.TotemService;
import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.List;
import java.util.Map;

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
    private TotemFletchingOverlay overlay;

    @Override
    protected void startUp() throws Exception {
        totemService = new TotemService();
        overlay = new TotemFletchingOverlay(this, totemService, client);
        overlayManager.add(overlay);
    }

    @Provides
    TotemFletchingConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(TotemFletchingConfig.class);
    }

    @Subscribe
    public void onVarbitChanged(final VarbitChanged varbitChanged) {
        if(varbitChanged.getVarbitId() < 17611 || varbitChanged.getVarbitId() > 17754) return;

        totemService.onVarbitChanged(varbitChanged);
    }

    @Subscribe
    public void onGameObjectSpawned(final GameObjectSpawned gameObjectSpawned) {
        totemService.addGameObject(gameObjectSpawned.getGameObject());
    }

    @Subscribe
    public void onGameObjectDespawned(final GameObjectDespawned gameObjectDespawned) {
        totemService.removeGameObject(gameObjectDespawned.getGameObject());
    }


}
