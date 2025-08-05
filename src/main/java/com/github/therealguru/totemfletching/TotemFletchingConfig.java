package com.github.therealguru.totemfletching;

import java.awt.Color;
import net.runelite.client.config.*;

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
            name = "Highlight Correct Chatbox Carving Options",
            description = "Highlights which animals to select in the Totem Carving UI",
            section = sectionOverlays,
            position = 0)
    default boolean highlightCorrectCarvingChoice() {
        return true;
    }

    @ConfigItem(
            keyName = "maskIncorrectCarvingChoice",
            name = "Highlight Correct Carving Choices",
            description = "Whether to mask the incorrect carving choices in the interface",
            section = sectionOverlays,
            position = 1)
    default boolean maskIncorrectCarvingChoice() {
        return true;
    }

    @ConfigItem(
            keyName = "renderTotemHighlight",
            name = "Show Totem Highlight",
            description = "Draw the highlight over totems",
            section = sectionOverlays,
            position = 2)
    default boolean renderTotemHighlight() {
        return true;
    }

    @ConfigItem(
            keyName = "renderTotemText",
            name = "Show Totem Text Overlay",
            description = "Draw the text overlay over totems for animals and decorations",
            section = sectionOverlays,
            position = 3)
    default boolean renderTotemText() {
        return true;
    }

    @ConfigItem(
            keyName = "renderPoints",
            name = "Show Points Text Overlay",
            description = "Draw the text overlay over points",
            section = sectionOverlays,
            position = 4)
    default boolean renderPoints() {
        return true;
    }

    @ConfigItem(
            keyName = "showZeroPoints",
            name = "Show Zero Points",
            description = "Show the points text even if the points are zero",
            section = sectionAdditional,
            position = 5)
    default boolean showZeroPoints() {
        return true;
    }

    @ConfigItem(
            keyName = "renderEntTrails",
            name = "Show Ent Trails",
            description =
                    "Whether to show the ent trails that need to be stepped on for bonus points",
            section = sectionOverlays,
            position = 6)
    default boolean renderEntTrails() {
        return true;
    }

    @ConfigSection(
            name = "Color Settings",
            description = "Pick colors for each overlay element",
            position = 1)
    String sectionColors = "sectionColors";

    @ConfigItem(
            keyName = "totemCompleteColor",
            name = "Completed Totem",
            description = "Choose the color used for the highlight on completed totems",
            section = sectionColors,
            position = 0)
    default Color totemCompleteColor() {
        return GREEN;
    }

    @ConfigItem(
            keyName = "totemIncompleteColor",
            name = "Incomplete Totem",
            description = "Choose the color used for the highlight on incomplete totems",
            section = sectionColors,
            position = 1)
    default Color totemIncompleteColor() {
        return RED;
    }

    @ConfigItem(
            keyName = "totemTextColor",
            name = "Totem Text",
            description = "Choose the color used for the text on the totem overlays",
            section = sectionColors,
            position = 2)
    default Color totemTextColor() {
        return DEFAULT_TEXT_COLOR;
    }

    @ConfigItem(
            keyName = "pointsTextColor",
            name = "Points Text",
            description = "Choose the color used for the text on the points overlays",
            section = sectionColors,
            position = 3)
    default Color pointsTextColor() {
        return DEFAULT_TEXT_COLOR;
    }

    @ConfigItem(
            keyName = "pointsCappedColor",
            name = "Points at Maximum Color",
            description =
                    "Choose the color used to highlight the points tile if the maximum point total has been reached",
            section = sectionColors,
            position = 4)
    default Color pointsCappedColor() {
        return RED;
    }

    @ConfigItem(
            keyName = "entTrailColor",
            name = "Ent Trail Color",
            description = "Choose the color used to draw ent trails",
            section = sectionColors,
            position = 5)
    default Color entTrailColor() {
        return Color.decode("#e4fff6");
    }

    @ConfigItem(
            keyName = "carvingInterfaceColor",
            name = "Carving Highlight Color",
            description =
                    "Choose the color used to highlight the correct actions in the carving UI",
            section = sectionColors,
            position = 6)
    default Color carvingInterfaceColor() {
        return Color.decode("#9CF575");
    }

    @Alpha
    @ConfigItem(
            keyName = "carvingInterfaceMask",
            name = "Carving Mask Color",
            description = "Choose the color to mask invalid options for carving totems",
            section = sectionColors,
            position = 7)
    default Color carvingMaskColor() {
        return new Color(64, 64, 64, 204);
    }

    @ConfigSection(
            name = "Additional Settings",
            description = "Customize additional accessibility settings",
            closedByDefault = true,
            position = 2)
    String sectionAdditional = "sectionAdditional";

    @ConfigItem(
            keyName = "keepDecoratedText",
            name = "Keep Fully Decorated Text",
            description = "Show the text on a fully decorated totem",
            section = sectionAdditional,
            position = 0)
    default boolean keepDecoratedText() {
        return false;
    }

    @ConfigItem(
            keyName = "unbuiltOffset",
            name = "Unbuilt Totem Offset",
            description = "Choose how high the unbuilt totem text will be from the ground",
            section = sectionAdditional,
            position = 1)
    @Units(Units.PIXELS)
    @Range(max = 800)
    default int unbuiltOffset() {
        return 160;
    }

    @ConfigItem(
            keyName = "builtOffset",
            name = "Built Totem Offset",
            description = "Choose how high the built totem text will be from the ground",
            section = sectionAdditional,
            position = 2)
    @Units(Units.PIXELS)
    @Range(max = 800)
    default int builtOffset() {
        return 160;
    }

    @ConfigItem(
            keyName = "pointsOffset",
            name = "Points Offset",
            description = "Choose how high the points text will be from the ground",
            section = sectionAdditional,
            position = 3)
    @Units(Units.PIXELS)
    @Range(max = 800)
    default int pointsOffset() {
        return 16;
    }

    @ConfigItem(
        keyName = "renderPanel",
        name = "Show panel when doing Totem fletching",
        description = "Highlights which animals to select in the Totem Carving UI",
        section = sectionOverlays,
        position = 7)
    default boolean renderPanel() {
        return true;
    }
}
