package com.github.therealguru.totemfletching;

import com.github.therealguru.totemfletching.overlay.TotemFonts;
import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("totem-fletching")
public interface TotemFletchingConfig extends Config {

    Color RED = Color.decode("#E45F5F");
    Color GREEN = Color.decode("#9CF575");
    Color DEFAULT_TEXT_COLOR = Color.decode("#ddc2ff");

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
            keyName = "renderTotemHighlight",
            name = "Show Totem Highlight",
            description = "Draw the highlight over totems",
            section = sectionOverlays,
            position = 1)
    default boolean renderTotemHighlight() {
        return true;
    }

    @ConfigItem(
            keyName = "renderTotemText",
            name = "Show Totem Text Overlay",
            description =
                    "Draw the text overlay over totems for animals and decorations",
            section = sectionOverlays,
            position = 2)
    default boolean renderTotemText() {
        return true;
    }

    @ConfigItem(
            keyName = "keepDecoratedText",
            name = "Keep Fully Decorated Text",
            description = "Show the text on a fully decorated totem",
            section = sectionOverlays,
            position = 3)
    default boolean keepDecoratedText() {
        return false;
    }

    @ConfigItem(
            keyName = "renderPointsText",
            name = "Show Points Text Overlay",
            description = "Draw the text overlay over points",
            section = sectionOverlays,
            position = 4)
    default boolean renderPointsText() {
        return true;
    }

    @ConfigItem(
            keyName = "renderEntTrails",
            name = "Show Ent Trails",
            description =
                    "Whether to show the ent trails that need to be stepped on for bonus points",
            section = sectionOverlays,
            position = 5)
    default boolean renderEntTrails() {
        return true;
    }

    @ConfigSection(
            name = "Color Settings",
            description = "Pick colors for each overlay element",
            position = 1)
    String sectionColors = "sectionColors";

    @ConfigItem(
            keyName = "carvingInterfaceColor",
            name = "Carving Highlight Color",
            description =
                    "Choose the color used to highlight the correct actions in the carving UI",
            section = sectionColors,
            position = 0)
    default Color carvingInterfaceColor() {
        return Color.decode("#9CF575");
    }

    @ConfigItem(
            keyName = "totemCompleteColor",
            name = "Completed Totem",
            description = "Choose the color used for the highlight on completed totems",
            section = sectionColors,
            position = 1)
    default Color totemCompleteColor() {
        return GREEN;
    }

    @ConfigItem(
            keyName = "totemIncompleteColor",
            name = "Incomplete Totem",
            description = "Choose the color used for the highlight on incomplete totems",
            section = sectionColors,
            position = 2)
    default Color totemIncompleteColor() {
        return RED;
    }

    @ConfigItem(
            keyName = "totemTextColor",
            name = "Totem Text",
            description = "Choose the color used for the text on the totem overlays",
            section = sectionColors,
            position = 3)
    default Color totemTextColor() {
        return DEFAULT_TEXT_COLOR;
    }

    @ConfigItem(
            keyName = "pointsTextColor",
            name = "Points Text",
            description = "Choose the color used for the text on the points overlays",
            section = sectionColors,
            position = 4)
    default Color pointsTextColor() {
        return DEFAULT_TEXT_COLOR;
    }

    @ConfigItem(
            keyName = "pointsCappedColor",
            name = "Points at Maximum Color",
            description =
                    "Choose the color used to highlight the points tile if the maximum point total has been reached",
            section = sectionColors,
            position = 5)
    default Color pointsCappedColor() {
        return RED;
    }

    @ConfigItem(
            keyName = "entTrailColor",
            name = "Ent Trail Color",
            description = "Choose the color used to draw ent trails",
            section = sectionColors,
            position = 6)
    default Color entTrailColor() {
        return Color.decode("#e4fff6");
    }

    @ConfigSection(
            name = "Text Settings",
            description = "Customize the overlay text",
            position = 2)
    String sectionText = "sectionText";

    @ConfigItem(
            keyName = "unbuiltHeight",
            name = "Unbuilt Height",
            description = "Choose the height of an unbuilt totem text",
            section = sectionText,
            position = 0)
    @Range(max = 500)
    default int unbuiltHeight() {
        return 16;
    }

    @ConfigItem(
            keyName = "builtHeight",
            name = "Built Height",
            description = "Choose the height of a built totem text",
            section = sectionText,
            position = 1)
    @Range(max = 500)
    default int builtHeight() {
        return 16;
    }

    @ConfigItem(
            keyName = "pointsHeight",
            name = "Points Height",
            description = "Choose the height of the points text",
            section = sectionText,
            position = 2)
    @Range(max = 500)
    default int pointsHeight() {
        return 16;
    }

    @ConfigItem(
            keyName = "overlayFont",
            name = "Overlay Font",
            description = "Choose the font type to be used for the overlay text",
            section = sectionText,
            position = 3)
    default TotemFonts overlayFont() {
        return TotemFonts.RUNESCAPE;
    }

    @ConfigItem(
            keyName = "useBoldFont",
            name = "Bold Style",
            description = "Use Bold font style instead",
            section = sectionText,
            position = 4)
    default Color carvingInterfaceColor() {
        return GREEN;
    }
}
