package com.github.therealguru.totemfletching;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("totem-fletching")
public interface TotemFletchingConfig extends Config {
    @ConfigSection(
            name = "Overlay Settings",
            description = "Enable or disable individual overlays",
            position = 0)
    String sectionOverlays = "sectionOverlays";

    @ConfigItem(
            keyName = "renderChatboxOptions",
            name = "Highlight Chatbox Carving Options",
            description = "Highlights which animals to select in the Totem Carving UI",
            section = sectionOverlays,
            position = 0)
    default boolean renderChatboxOptions() {
        return true;
    }

    @ConfigItem(
            keyName = "renderTotemOverlays",
            name = "Show Totem Overlays",
            description = "Draw the overlay over totems",
            section = sectionOverlays,
            position = 1)
    default boolean renderTotemOverlays() {
        return true;
    }

    @ConfigItem(
            keyName = "renderTextOverlays",
            name = "Show Totem Text Overlays",
            description =
                    "Draw the text overlay over totems for animals and total points for the site",
            section = sectionOverlays,
            position = 2)
    default boolean renderTextOverlays() {
        return true;
    }

    @ConfigItem(
            keyName = "renderPoints",
            name = "Show Points Overlay",
            description = "Draw the overlay over totems",
            section = sectionOverlays,
            position = 3)
    default boolean renderPoints() {
        return true;
    }

    @ConfigItem(
            keyName = "renderEntTrails",
            name = "Show Ent Trails",
            description =
                    "Whether to show the ent trails that need to be stepped on for bonus points",
            section = sectionOverlays,
            position = 4)
    default boolean renderEntTrails() {
        return true;
    }

    @ConfigSection(
            name = "Color Settings",
            description = "Pick colors for each overlay element",
            position = 1)
    String sectionColors = "sectionColors";

    @ConfigItem(
            keyName = "overlayTextColor",
            name = "Overlay Text",
            description = "Choose the color used for the text on the totem overlays",
            section = sectionColors,
            position = 0)
    default Color overlayTextColor() {
        return Color.decode("#ddc2ff");
    }

    @ConfigItem(
            keyName = "totemCompleteColor",
            name = "Completed Totem",
            description = "Choose the color used for the overlay on completed totems",
            section = sectionColors,
            position = 1)
    default Color totemCompleteColor() {
        return Color.decode("#e3ffd7");
    }

    @ConfigItem(
            keyName = "totemIncompleteColor",
            name = "Incomplete Totem",
            description = "Choose the color used for the overlay on incomplete totems",
            section = sectionColors,
            position = 2)
    default Color totemIncompleteColor() {
        return Color.decode("#e5a3a3");
    }

    @ConfigItem(
            keyName = "entTrailColor",
            name = "Ent Trail Color",
            description = "Choose the color used to draw ent trails",
            section = sectionColors,
            position = 3)
    default Color entTrailColor() {
        return Color.decode("#e4fff6");
    }
}
