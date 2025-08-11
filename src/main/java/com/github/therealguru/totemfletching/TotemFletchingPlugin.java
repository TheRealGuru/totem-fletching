package com.github.therealguru.totemfletching;

import com.github.therealguru.totemfletching.listener.PluginEventListener;
import com.github.therealguru.totemfletching.service.OverlayService;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(name = "Totem Fletching")
public class TotemFletchingPlugin extends Plugin {

    @Inject private EventBus eventBus;
    @Inject private PluginEventListener pluginEventListener;
    @Inject private OverlayService overlayService;

    @Override
    protected void startUp() throws Exception {
        eventBus.register(pluginEventListener);
        overlayService.registerOverlays();
    }

    @Override
    protected void shutDown() throws Exception {
        eventBus.unregister(pluginEventListener);
        overlayService.unregisterOverlays();
    }

    @Provides
    TotemFletchingConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(TotemFletchingConfig.class);
    }
}
