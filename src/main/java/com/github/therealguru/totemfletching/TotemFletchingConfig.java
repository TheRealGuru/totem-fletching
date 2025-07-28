package com.github.therealguru.totemfletching;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("totem-fletching")
public interface TotemFletchingConfig extends Config {

    @ConfigItem(
            keyName = "renderEntTrails",
            name = "Show Ent Trails",
            description = "Whether to show the ent trails that need to be stepped on for bonus points"
    )
    default boolean renderEntTrails() {
        return true;
    }

    @ConfigItem(
            keyName = "renderCarvingOverlay",
            name = "Highlight Animal Overlay",
            description = "Highlights which animals to select in the UI"
    )
    default boolean renderCarvingOverlay() {
        return true;
    }
}
